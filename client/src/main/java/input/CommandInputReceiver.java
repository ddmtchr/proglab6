package input;

import client.ConnectionProvider;
import client.ScriptExecutor;
import command.*;
import exceptions.ErrorInScriptException;
import exceptions.NoSuchCommandException;
import exceptions.WrongCommandFormatException;
import utility.LabWorkStatic;
import utility.Request;

import java.util.HashMap;
import java.util.Scanner;

public class CommandInputReceiver extends InputReceiver {
    private HashMap<String, Command> commandMap = new HashMap<>();
    private FieldInputReceiver fir;

    public CommandInputReceiver(Scanner s) {
        super(s);
        fir = new FieldInputReceiver(s);
        CollectionCommandReceiver cr = new CollectionCommandReceiver();
        Command[] commandArray = new Command[]{
                new HelpCommand(cr, 0, 0),
                new InfoCommand(cr, 0, 0),
                new ShowCommand(cr, 0, 0),
                new AddCommand(cr, 0, 1),
                new UpdateCommand(cr, 1, 1),
                new RemoveByIdCommand(cr, 1, 0),
                new ClearCommand(cr, 0, 0),
                new InsertAtCommand(cr, 1, 1),
                new AddIfMinCommand(cr, 0, 1),
                new RemoveGreaterCommand(cr, 0, 1),
                new AverageOfMinimalPointCommand(cr, 0, 0),
                new MinByIdCommand(cr, 0, 0),
                new PrintFieldAscendingMinimalPointCommand(cr, 0, 0)
        };
        for (Command cmd : commandArray) {
            commandMap.put(cmd.toString(), cmd);
        }
    }

    public Request createRequest(ConnectionProvider connectionProvider) {
        String[] command = parseCommand();
        try {
            if (command[0].equals("exit")) {
                if (command[1].equals(" ")) {
                    System.out.println("Выход из программы...");
                    System.exit(0);
                } else {
                    System.out.println("Использование: exit");
                }
            } else if (command[0].equals("execute_script")) {
                String[] argsArray = command[1].split("\\s+");
                if (argsArray.length != 1) {
                    System.out.println("Использование: execute_script {file_name}");
                } else {
                    ScriptExecutor scriptExecutor = new ScriptExecutor();
                    scriptExecutor.executeScript(command[1], connectionProvider);


                }
            } else {
                Request request = pack(command);
                return request;
            }

        } catch (ErrorInScriptException e) {

        } catch (NoSuchCommandException e) {
            System.out.println("Команды " + command[0] + " не существует, введите help для справки");
        }
        return null;
    }

    public String[] parseCommand() {
        String input = getScanner().nextLine().trim();
        String cmd, args;
        if (input.contains(" ")) {
            cmd = input.substring(0, input.indexOf(' '));
            args = input.substring(input.indexOf(' ') + 1).trim();
        } else {
            cmd = input;
            args = " ";
        }
        return new String[]{cmd, args};
    }

    public boolean validateCommand(Command cmd, String stringArgs) throws NoSuchCommandException {
        try {
            if (cmd == null) throw new NoSuchCommandException();
            if (!cmd.checkArgsNumber(stringArgs)) throw new WrongCommandFormatException();
            return true;
        } catch (WrongCommandFormatException e) {
            System.out.println("Использование: " + cmd.toStringWithArgs());
            return false;
        }
    }

    public Request pack(String[] command) throws NoSuchCommandException, ErrorInScriptException {
        Command cmd = commandMap.get(command[0]);
        if (validateCommand(cmd, command[1])) {
            String stringArgs = command[1];
            LabWorkStatic lws = null;
            if (needObject(cmd)) {
                lws = fir.enterLabWork();
            }
            return new Request(cmd, stringArgs, lws);
        }
        return null;
    }

    public boolean needObject(Command cmd) {
        return cmd.getObjectsNumber() == 1;
    }

    public HashMap<String, Command> getCommandMap() {
        return commandMap;
    }

    public FieldInputReceiver getFir() {
        return fir;
    }
}
