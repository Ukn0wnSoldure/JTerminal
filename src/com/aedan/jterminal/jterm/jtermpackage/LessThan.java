package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.command.commandarguments.ArgumentType;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

class LessThan extends Command {
    LessThan() {
        super("lt", "Returns true if a number is less than another number.");
    }

    @Override
    public void parse(ArgumentList args, CommandInput input, CommandOutput output, Environment environment) throws JTerminalException {
        args.checkMatches(this, ArgumentType.DOUBLE, ArgumentType.DOUBLE);

        output.println(Double.parseDouble(args.get(1).toString()) < Double.parseDouble(args.get(2).toString()));
    }
}
