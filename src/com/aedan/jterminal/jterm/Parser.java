package com.aedan.jterminal.jterm;

import com.aedan.jterminal.command.commandhandler.CommandHandler;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.output.StringOutput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for parsing .jterm files.
 */

final class Parser {

    /**
     * Pattern for finding functions in .jterm files.
     */
    private static Pattern functionPattern = Pattern.compile("([!a-zA-Z]+)\\(([^()]*)\\) *\\{");

    /**
     * Parsers a .jterm file.
     *
     * @param s       The file to parse.
     * @param runtime The runtime requesting the parser.
     * @return The list of functions in the file.
     * @throws CommandHandler.CommandHandlerException If there was an error parsing the file.
     */
    public static ArrayList<Function> parse(String s, JTermRuntime runtime) throws CommandHandler.CommandHandlerException {
        ArrayList<Function> functions = new ArrayList<>();
        Matcher m = functionPattern.matcher(s);
        while (m.find()) {
            int depth = 0, i = m.start();
            for (; i < s.length(); i++) {
                if (s.charAt(i) == '{')
                    depth++;
                if (s.charAt(i) == '}') {
                    depth--;
                    if (depth == 0) break;
                }
            }
            String fn = s.substring(m.end(), i);
            functions.add(parseFunction(fn, m.group(1), m.group(2), runtime));
        }
        return functions;
    }

    /**
     * Parses a function.
     *
     * @param src       The source to parse the function from.
     * @param name      The name of the function.
     * @param arguments The arguments of the function.
     * @param runtime   The runtime requesting the parse.
     * @return The parsed function.
     * @throws CommandHandler.CommandHandlerException If there was an error parsing the function.
     */
    private static Function parseFunction(String src, String name, String arguments, JTermRuntime runtime)
            throws CommandHandler.CommandHandlerException {
        CommandOutput commandOutput = runtime.getEnvironment().getCommandHandler().getCommandOutput();
        if (name.startsWith("!")) {
            name = name.substring(1);
            commandOutput = new StringOutput();
        }
        String finalName = name;
        CommandOutput finalCommandOutput = commandOutput;
        return new Function() {
            @Override
            public String getIdentifier() {
                return finalName;
            }

            @Override
            public Object apply(Object[] o) throws CommandHandler.CommandHandlerException {
                HashMap<String, Object> sVars = runtime.getEnvironment().getGlobalVariables();
                runtime.getEnvironment().setGlobalVariables(new HashMap<>());
                // Gets function arguments
                String[] args = arguments.split(",");
                if (arguments.isEmpty()) {
                    args = new String[0];
                }
                // Adds function arguments to the stack
                for (int i = 0; i < args.length; i++) {
                    try {
                        runtime.getEnvironment().addGlobalVariable(args[i].trim(), o[i].toString());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new CommandHandler.CommandHandlerException("Not enough arguments given for function " + finalName);
                    }
                }
                // Execute statements
                for (String statement : src.split("\n")) {
                    runtime.getEnvironment().getCommandHandler().handleInput(finalCommandOutput, statement);
                }
                // Ends scope
                runtime.getEnvironment().setGlobalVariables(sVars);
                if (finalCommandOutput instanceof StringOutput)
                    return ((StringOutput) finalCommandOutput).getString();
                else
                    return null;
            }
        };
    }
}