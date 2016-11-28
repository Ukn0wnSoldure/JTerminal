package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

/**
 * Created by Aedan Smith.
 */

class GreaterThan extends Command {
    GreaterThan() {
        super("gt", "Returns true if a number is greater than another number.");
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment) throws JTerminalException {
        args.checkMatches(this, String.class, String.class);

        return (double) args.get(1).value > (double) args.get(2).value;
    }
}
