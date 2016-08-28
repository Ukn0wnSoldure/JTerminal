package com.aedan.jterminal.commands.default_package.FileIO;

import com.aedan.jterminal.Directory;
import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;
import com.aedan.jterminal.utils.FileUtils;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Default Command.
 */

class RemoveFile extends Command {

    RemoveFile() {
        super("rm", "Removes the file with the given name.");
    }

    @Override
    public void parse(CommandInput input, String[] args, Directory directory, CommandOutput output) throws CommandHandler.CommandHandlerException {
        try {
            output.println(FileUtils.removeFile(directory.getFile(args[1])));
        } catch (FileUtils.FileIOException e) {
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}
