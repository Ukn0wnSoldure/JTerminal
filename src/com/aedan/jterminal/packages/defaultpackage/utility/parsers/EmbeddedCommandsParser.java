package com.aedan.jterminal.packages.defaultpackage.utility.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.Environment;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith on 10/10/2016.
 * <p>
 * Parser for embedded command.
 */

public class EmbeddedCommandsParser implements Parser<StringIterator, ArgumentList> {
    private Environment environment;

    public EmbeddedCommandsParser(Environment environment){
        this.environment = environment;
    }

    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (in.peek() != '[')
            return false;
        in.next();

        ArgumentList arguments = new ArgumentList();
        // TODO: Nested predicate
        environment.getCommandHandler().getParser().parseUntil(
                in, arguments, stringIterator -> stringIterator.peek() != ']'
        );
        in.next();

        argumentList.add(new Argument() {
            private Object o = null;

            @Override
            public Object get() {
                if (o == null) {
                    o = environment.getCommandHandler().handleInput(
                            arguments,
                            environment.getInput(),
                            environment.getOutput()
                    );
                }
                return o;
            }

            @Override
            public Class<?> getArgumentType() {
                return get().getClass();
            }

            @Override
            public String toString() {
                return get().toString();
            }
        });
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}