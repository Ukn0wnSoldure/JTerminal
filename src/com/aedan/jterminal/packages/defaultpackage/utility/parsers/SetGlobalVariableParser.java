package com.aedan.jterminal.packages.defaultpackage.utility.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default Operand.
 */

public class SetGlobalVariableParser implements Parser {
    @Override
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '=')
            return false;
        in.next();

        // TODO: Remove getLast()
        String name = parser.parse(environment, in.untilCurrent()).getLast().toString();
        Object value = parser.parse(environment, in.fromCurrent());

        environment.addGlobalVariable(name, value);
        argumentList.clear();
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}