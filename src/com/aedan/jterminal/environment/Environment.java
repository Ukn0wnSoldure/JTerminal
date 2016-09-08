package com.aedan.jterminal.environment;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandFormat;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.CommandPackage;
import com.aedan.jterminal.environment.variables.GlobalVariable;
import com.aedan.jterminal.environment.variables.Variable;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Aedan Smith on 9/6/2016.
 *
 * Class containing the Environment of the JTerminal.
 */

public class Environment {

    /**
     * The List of Commands in the Environment.
     */
    private ArrayList<Command> commands = new ArrayList<>();

    /**
     * The List of CommandFormats in the Environment.
     */
    private ArrayList<CommandFormat> commandFormats = new ArrayList<>();

    /**
     * The List of Environment Variables in the Environment
     */
    private ArrayList<Variable> environmentVariables = new ArrayList<>();

    /**
     * The List of Variables in the Environment.
     */
    private ArrayList<GlobalVariable> globalVariables = new ArrayList<>();

    /**
     * The Directory of the Environment.
     */
    private Directory directory;

    /**
     * The CommandHandler for the Environment.
     */
    private CommandHandler commandHandler;

    /**
     * Default Environment constructor.
     *
     * @param commandPackages The List of CommandPackages for the Environment.
     */
    public Environment(CommandPackage... commandPackages){
        this.commandHandler = new CommandHandler(this);
        for (CommandPackage c : commandPackages){
            this.addCommandPackage(c);
        }
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()){
            environmentVariables.add(new Variable() {
                @Override
                public String getName() {
                    return envName;
                }

                @Override
                public String getValue() {
                    return env.get(envName);
                }
            });
        }
        this.directory = new Directory();
        this.environmentVariables.add(0, directory);
        this.environmentVariables.add(new Variable() {
            @Override
            public String getName() {
                return "COMMANDPROPERTIES";
            }

            @Override
            public String getValue() {
                String s = "";
                for (Command c : getCommands()){
                    s += "\n" + c.getIdentifier() + ":\n";
                    for (int i = 0; i < Command.numProperties; i++) {
                        try {
                            s += "[" + i + "] " + c.getProperty(i).replace("\n", "\n    ") + "\n";
                        } catch (Command.InvalidPropertyException e) {
                            s += "[" + i + "] " + e.getMessage() + "\n";
                        }
                    }
                }
                return s;
            }
        });
        this.environmentVariables.add(new Variable() {
            @Override
            public String getName() {
                return "COMMANDS";
            }

            @Override
            public String getValue() {
                String s = "";
                for (Command c : getCommands()){
                    s += c.getIdentifier() + "\n";
                }
                return s;
            }
        });
        this.environmentVariables.add(new Variable() {
            @Override
            public String getName() {
                return "COMMANDFORMATS";
            }

            @Override
            public String getValue() {
                String s = "";
                for (CommandFormat c : getCommandFormats()){
                    s += c.getClass().getSimpleName() + "\n";
                }
                return s;
            }
        });
        this.environmentVariables.add(new Variable() {
            @Override
            public String getName() {
                return "GLOBALVARS";
            }

            @Override
            public String getValue() {
                String s = "";
                for (GlobalVariable v : getGlobalVariables()){
                    s += v.getName() + "\n";
                }
                return s;
            }
        });
        this.environmentVariables.add(new Variable() {
            @Override
            public String getName() {
                return "ENVVARS";
            }

            @Override
            public String getValue() {
                String s = "";
                for (Variable v : getEnvironmentVariables()){
                    s += v.getName() + "\n";
                }
                return s;
            }
        });
    }

    /**
     * Adds a GlobalVariable to the Environment.
     *
     * @param variable The GlobalVariable to add.
     */
    @NotNull
    public void addGlobalVariable(GlobalVariable variable) {
        removeGlobalVariable(variable.getName());
        globalVariables.add(variable);
    }

    /**
     * Removes a GlobalVariable from the Environment.
     *
     * @param name The name of the GlobalVariable to remove.
     */
    public void removeGlobalVariable(String name) {
        GlobalVariable n = null;
        for (GlobalVariable v : globalVariables) {
            if (Objects.equals(v.getName(), name)) {
                n = v;
            }
        }
        if (n != null)
            globalVariables.remove(n);
    }

    /**
     * Adds a CommandPackage to the Environment.
     *
     * @param commandPackage The CommandPackage to add.
     */
    @NotNull
    public void addCommandPackage(CommandPackage commandPackage) {
        commandPackage.addCommands(this);
    }

    /**
     * Adds a CommandFormat to the Environment.
     *
     * @param commandFormat The CommandFormat to add.
     */
    @NotNull
    public void addCommandFormat(CommandFormat commandFormat) {
        commandFormats.add(commandFormat);
    }

    /**
     * Adds a Command to the Environment.
     *
     * @param command The Command to add.
     */
    @NotNull
    public void addCommand(Command command) {
        commands.add(command);
        commands.sort((o1, o2) -> o2.getIdentifier().length() - o1.getIdentifier().length());
    }

    public void handleInput(CommandInput input, CommandOutput output) throws CommandHandler.CommandHandlerException {
        commandHandler.handleInput(input, output);
    }

    public void handleInput(CommandInput input, String s, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        commandHandler.handleInput(input, s, output);
    }

    public String compute(CommandInput input, String s) throws CommandHandler.CommandHandlerException {
        return commandHandler.compute(input, s);
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public Directory getDirectory(){
        return directory;
    }

    public void addEnvironmentVariable(Variable v){
        environmentVariables.add(v);
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public ArrayList<CommandFormat> getCommandFormats() {
        return commandFormats;
    }

    public ArrayList<Variable> getEnvironmentVariables() {
        return environmentVariables;
    }

    public ArrayList<GlobalVariable> getGlobalVariables() {
        return globalVariables;
    }

}