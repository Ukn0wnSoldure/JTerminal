package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;
import com.aedan.jterminal.output.StringOutput;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default Operand.
 */

public class SetGlobalVariableParser implements ParseRule {
    @Override
    public boolean matches(String s, int i) {
        return s.charAt(i) == '=';
    }

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        StringOutput value = new StringOutput(), name = new StringOutput();
        environment.getCommandHandler().handleInput(s.substring(i + 1), environment.getInput(), value);
        environment.getCommandHandler().handleInput(s.substring(0, i), environment.getInput(), name);

        environment.addGlobalVariable(name.getString().trim(), value.getString().trim());
        argumentList.clear();
        return s.length();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
