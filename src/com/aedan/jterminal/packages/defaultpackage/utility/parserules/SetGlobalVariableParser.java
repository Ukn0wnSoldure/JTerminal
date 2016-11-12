package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.TokenList;
import com.aedan.jterminal.output.StringOutput;

/**
 * Created by Aedan Smith on 8/22/2016.
 * <p>
 * Default Operand.
 */

public class SetGlobalVariableParser implements ParseRule {
    @Override
    public char getIdentifier() {
        return '=';
    }

    @Override
    public int process(Environment environment, String s, int i, TokenList tokenList) throws JTerminalException {
        StringOutput value = new StringOutput(), name = new StringOutput();
        environment.getCommandHandler().handleInput(environment.getInput(), value, s.substring(i + 1));
        environment.getCommandHandler().handleInput(environment.getInput(), name, s.substring(0, i));

        environment.addGlobalVariable(name.getString().trim(), value.getString().trim());
        tokenList.clear();
        return s.length();
    }
}
