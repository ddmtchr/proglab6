package utility;

import command.Command;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {
    private SocketAddress host;
    private Command cmd;
    private String stringArgs;
    private Serializable objectArgs;

    public Request(Command cmd, String stringArgs, Serializable objectArgs) {
        this.cmd = cmd;
        this.stringArgs = stringArgs;
        this.objectArgs = objectArgs;
    }

    public Command getCommand() {
        return cmd;
    }

    public String getStringArgs() {
        return stringArgs;
    }

    public Serializable getObjectArgs() {
        return objectArgs;
    }

    public void setHost(SocketAddress host) {
        this.host = host;
    }

    public SocketAddress getHost() {
        return host;
    }

    public boolean isEmpty() {
        return cmd == null && stringArgs.isEmpty() && objectArgs == null;
    }

//    public String toString() {
//
//        String ob = "null";
//        return cmd.toString() + " " + stringArgs + ob;
//    }
}
