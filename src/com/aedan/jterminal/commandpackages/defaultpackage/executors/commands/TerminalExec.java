package com.aedan.jterminal.commandpackages.defaultpackage.executors.commands;

import com.aedan.jterminal.commands.Command;
import com.aedan.jterminal.commands.CommandHandler;
import com.aedan.jterminal.commands.commandarguments.ArgumentType;
import com.aedan.jterminal.commands.commandarguments.CommandArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.CommandInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by Aedan Smith on 10/6/2016.
 *
 * Default command.
 */

public class TerminalExec extends Command {

    public TerminalExec() {
        super("exect");
        this.properties[0] = "Executes a command via the terminal.";
        this.properties[1] = "exect [string-command]:" +
                "    Executes command [string-command] in the System terminal (currently only Windows).";
    }

    @Override
    public void parse(CommandInput input, CommandArgumentList args, Environment environment, CommandOutput output)
            throws CommandHandler.CommandHandlerException {
        try {
            args.checkMatches(ArgumentType.STRING);

            Process process = Runtime.getRuntime().exec("cmd");
            for (PrintStream p : output.getPrintStreams()) {
                new Thread(new SyncPipe(process.getErrorStream(), p)).start();
                new Thread(new SyncPipe(process.getInputStream(), p)).start();
            }
            PrintWriter stdin = new PrintWriter(process.getOutputStream());
            for (String s : args.get(1).value.split("(?<!\\\\);")){
                stdin.println(s);
            }
            stdin.close();
            output.println("\n\nReturn code: " + process.waitFor());
        } catch (Exception e){
            throw new CommandHandler.CommandHandlerException(e.getMessage());
        }
    }

}

class SyncPipe implements Runnable {
    public SyncPipe(InputStream istrm, OutputStream ostrm) {
        istrm_ = istrm;
        ostrm_ = ostrm;
    }

    public void run() {
        try {
            final byte[] buffer = new byte[1024];
            for (int length = 0; (length = istrm_.read(buffer)) != -1; ) {
                ostrm_.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final OutputStream ostrm_;
    private final InputStream istrm_;
}