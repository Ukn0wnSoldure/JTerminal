package com.aedan.jterminal.commandpackages.defaultpackage.io.commandformats;

import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default CommandFormat.
 */

public class OutputToFile implements CommandFormat {

    private final String outputToFileRegex = "([^>]+)>> *(.+)";
    private final Pattern outputToFilePattern = Pattern.compile(outputToFileRegex);

    @Override
    public boolean matches(String in) throws CommandHandler.CommandHandlerException {
        return in.matches(outputToFileRegex);
    }

    @Override
    public void handleInput(Environment environment, CommandInput input, String in, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            Matcher m = outputToFilePattern.matcher(in);
            if (m.find()) {
                File f = environment.getDirectory().getFile(environment.compute(input, m.group(2)));
                output.println(FileUtils.createFile(f));
                PrintStream ps = new PrintStream(new FileOutputStream(f));
                output.setPrintStreams(ps);
                environment.handleInput(input, m.group(1), output);
                ps.close();
            } else {
                throw new CommandHandler.CommandHandlerException(
                        "\"" + in + "\" does not match CommandOutput To File format (command >> destination).");
            }
        } catch (Exception e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}