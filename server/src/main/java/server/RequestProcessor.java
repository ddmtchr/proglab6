package server;

import command.Command;
import command.CommandExecutor;
import utility.Request;

import java.io.Serializable;

public class RequestProcessor {
    protected int processRequest(Request r) {
        Command cmd = r.getCommand();
        String stringArgs = r.getStringArgs();
        Serializable objectArgs = r.getObjectArgs();
        CommandExecutor executor = new CommandExecutor();
        int execCode = executor.execute(cmd, stringArgs, objectArgs);

        return execCode;
    }
}
