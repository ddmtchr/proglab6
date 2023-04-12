package command;


import exceptions.WrongCommandFormatException;

import java.io.Serializable;

public abstract class AbstractCommand implements Command, Serializable {
    private final CollectionCommandReceiver data;
    private final int ARGS_NUMBER;
    private final int OBJECTS_NUMBER;
    protected AbstractCommand(CollectionCommandReceiver cr, int argsNumber, int obNumber) {
        data = cr;
        ARGS_NUMBER = argsNumber;
        OBJECTS_NUMBER = obNumber;
    }

    public int invoke(String stringArgs, Object objectArgs) {
        int execCode;
        try {
            if (!checkArgsNumber(stringArgs)) throw new WrongCommandFormatException();
            execCode = execute(stringArgs, objectArgs);
        } catch (WrongCommandFormatException e) {
            execCode = 1;
            System.out.println("Использование: " + toStringWithArgs());
        }
        return execCode;
    }

    @Override
    public boolean checkArgsNumber(String stringArgs) {
        String[] argsArray = stringArgs.split("\\s+");
        return argsArray.length == getArgsNumber();
    }

    public int getArgsNumber() {
        return ARGS_NUMBER;
    }

    public int getObjectsNumber() {
        return OBJECTS_NUMBER;
    }

    public CollectionCommandReceiver getImplementations() {
        return data;
    }

    public abstract String toStringWithArgs();
    public abstract int execute(String stringArgs, Object objectArgs);
}
