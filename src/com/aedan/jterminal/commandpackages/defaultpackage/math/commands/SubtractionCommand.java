package com.aedan.jterminal.commandpackages.defaultpackage.math.commands;

import com.aedan.jterminal.commandpackages.defaultpackage.math.MathCommand;

/**
 * Created by Aedan Smith on 9/5/2016.
 *
 * Default Command.
 */

public class SubtractionCommand extends MathCommand {

    public SubtractionCommand() {
        super("-");
        properties[0] = "Subtracts two numbers.";
    }

    @Override
    public long apply(long l1, long l2) {
        return l1 - l2;
    }

    @Override
    public double apply(double d1, double d2) {
        return d1 - d2;
    }


}