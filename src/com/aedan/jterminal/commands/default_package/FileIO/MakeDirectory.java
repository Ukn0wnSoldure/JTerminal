package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandArgument;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

public class MakeDirectory extends Command {

    MakeDirectory() {
        super("mkdir", "Creates a directory with the given name.");
    }

    @Override
    public void parse(CommandInput input, CommandArgument[] args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        try {
            output.println(FileUtils.createDirectory(directory.getFile(args[1].value)));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
