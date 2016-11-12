package com.aedan.jterminal.packages.defaultpackage.utility.commands;

import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.command.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

public class Set extends Command {
    public Set() {
        super("set", "Sets an environment variable");
        properties[1] = "set [string-var] [string-value]:\n" +
                "    Sets environment variable [string-var] to [string-value].";
    }

    @Override
    public void parse(CommandArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, ArgumentType.STRING, ArgumentType.STRING);

        environment.getEnvironmentVariables().put(args.get(1).value, args.get(2).value);
    }
}