package jterm.jtermpackage;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.Command;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

class IfEqual extends Command {
    IfEqual() {
        super("eq", "Returns true if two values are equal");
    }

    @Override
    public Object apply(ArgumentList args, CommandInput input, CommandOutput output, Environment environment)
            throws JTerminalException {
        args.checkMatches(this, String.class, String.class);

        return Objects.equals(args.get(1).value, args.get(2).value);
    }
}