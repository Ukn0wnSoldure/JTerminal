package com.aedan.jterminal.jterm;

import com.aedan.jterminal.JTerminalException;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for creating functions to be used in the JTermRuntime.
 */

public interface Function {
    /**
     * @return The name of the function.
     */
    String getIdentifier();

    /**
     * Applies a function.
     *
     * @param args The arguments of the function.
     * @return The return value of the Function.
     * @throws JTerminalException If there was an error applying the function.
     */
    Object apply(Object[] args) throws JTerminalException;
}
