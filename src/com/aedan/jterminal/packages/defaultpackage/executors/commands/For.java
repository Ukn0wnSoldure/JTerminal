package com.aedan.jterminal.packages.defaultpackage.executors.commands;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.MatchResult;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.Collection;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class For extends Command {
    public For() {
        super("for");
        this.properties[0] = "Iterates a command and stores the current iteration in a variable.";
        this.properties[1] =
                "for [int-begin] [int-end] [string-varname] [string-command]:\n" +
                        "    Adds the variable [string-varname] to the CommandHandler, then executes [string-command] once\n" +
                        "    for each value between [int-begin] and [int-end].\n" +
                        "for [string-content] [string-command]:\n" +
                        "    Adds the variable s to the CommandHandler, then executes [string-command] once for each line\n" +
                        "    in [string-content], setting variable s to the content of the line.";
    }

    @Override
    public Object parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        if (args.matches(Object.class, Object.class) == MatchResult.CORRECT_ARGS) {
            Object[] objects;
            if (args.get(1).value.getClass().isArray()) {
                objects = (Object[]) args.get(1).value;
            } else if (args.get(1).value.getClass().isAssignableFrom(Collection.class)) {
                objects = ((Collection) args.get(1).value).toArray();
            } else {
                objects = args.get(1).value.toString().split("\n");
            }
            Object[] out = new Object[objects.length];

            for (int i = 0; i < out.length; i++) {
                environment.addGlobalVariable("s", objects[i]);
                out[i] = environment.getCommandHandler().handleInput(args.get(2).toString(), input, output);
                environment.removeGlobalVariable("s");
            }

            return out;
        } else if (args.matches(Number.class, Number.class, String.class) == MatchResult.CORRECT_ARGS) {
            int max = ((Number) args.get(2).value).intValue();
            Object[] out = new Object[max];

            for (int i = ((Number) args.get(1).value).intValue(); i < max; i++) {
                environment.addGlobalVariable("i", i);
                out[i] = environment.getCommandHandler().handleInput(args.get(3).toString(), input, output);
                environment.removeGlobalVariable("i");
            }

            return out;
        } else {
            throw new JTerminalException("Incorrect arguments given", this);
        }
    }
}
