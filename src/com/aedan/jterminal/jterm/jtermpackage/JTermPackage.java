package com.aedan.jterminal.jterm.jtermpackage;

import com.aedan.jterminal.command.Package;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.JTermRuntime;
import com.aedan.jterminal.packages.defaultpackage.DefaultPackage;

/**
 * Created by Aedan Smith.
 */

public class JTermPackage implements Package {
    private JTermRuntime jTermRuntime;

    public JTermPackage(JTermRuntime jTermRuntime) {
        this.jTermRuntime = jTermRuntime;
    }

    @Override
    public void addTo(Environment environment) {
        environment.addPackage(new DefaultPackage());
        environment.addCommand(new CallFunction(jTermRuntime));
        environment.addCommand(new IfCommand());
        environment.addCommand(new com.aedan.jterminal.jterm.jtermpackage.IfElseCommand());
        environment.addCommand(new com.aedan.jterminal.jterm.jtermpackage.WhileCommand());
        environment.addCommand(new IfEqual());
        environment.addCommand(new IfNotEqual());
        environment.addCommand(new com.aedan.jterminal.jterm.jtermpackage.LessThan());
        environment.addCommand(new com.aedan.jterminal.jterm.jtermpackage.GreaterThan());
    }

    @Override
    public String toString() {
        return "JTermPackage";
    }
}