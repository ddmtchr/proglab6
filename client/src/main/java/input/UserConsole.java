package input;

import command.CommandExecutor;
import exceptions.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class for handling user input in general.
 */
public class UserConsole {
    private static final Scanner sc = new Scanner(System.in);
    private static final FieldInputReceiver fir = new FieldInputReceiver(sc);
    private static final CommandInputReceiver cir = new CommandInputReceiver(sc);
    private static final CommandExecutor commandExecutor = new CommandExecutor();
    private static final String[] greetings = new String[]{
            "Как ваше ничего? ",
            "Добрый вечер. ",
            "Guten Tag! ",
            "Konnichiwa! ",
            "Храни Вас Господь. "
    };
    private static ArrayList<String> scriptStack = new ArrayList<>();

    /**
     * Greets the user with random greeting from the list.
     */
    private static void greet() {
        Random generator = new Random();
        int greetingNumber = generator.nextInt(greetings.length);
        System.out.println(greetings[greetingNumber] + "Введите help для справки.");
    }

    /**
     * Parses the input string into a command and arguments
     * @param fir InputReceiver for use to parse the string
     * @return Array that separately contains command[0] and arguments[1]
     */
    private static String[] parseCommand(FieldInputReceiver fir) {
        String input = fir.getScanner().nextLine().trim();
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

    /**
     * Initializes the collection (reads it from file).
     */
//    public static void init() {
//        FileManager fileManager = new FileManager();
//        if (!fileManager.isFileValid()) System.exit(0);
//        if (!fileManager.readFromFile()) System.exit(0);
//        CollectionManager.setLastInitTime();
//        greet();
//    }

    /**
     * Gets the command and arguments and executes it.
     */
//    public static void interact() {
//        while (true) {
//            try {
//                String[] command = parseCommand(fir);
//                commandExecutor.execute(command[0], command[1]);
//            } catch (NoSuchElementException e) {
//                System.out.println("Ввод завершен пользователем");
//                System.exit(0);
//            }
//        }
//    }

    /**
     * Interacts with script file when executing execute_script command.
     * @param scriptName Name of the file containing the script
     * @return Execution result code
     */
//    public static int runScript(String scriptName) {
//        int execCode;
//        int execCommandCode;
//        try {
//            scriptStack.add(scriptName);
//            File scriptFile = new File(scriptName);
//            if (!scriptFile.exists()) throw new FileNotFoundException();
//            if (!scriptFile.canRead()) throw new SecurityException();
//            Scanner scriptScanner = new Scanner(scriptFile);
//            if (!scriptScanner.hasNextLine()) throw new EmptyScriptException();
//            Scanner latestScanner = fir.getScanner();
//            fir.setFileReadMode(true);
//            fir.setScanner(scriptScanner);
//            do {
//                String[] command = parseCommand(fir);
//                if (command[0].equals("execute_script")) {
//                    for (String script : scriptStack) {
//                        if (command[1].equals(script)) {
//                            fir.setFileReadMode(false);
//                            fir.setScanner(latestScanner);
//                            throw new ScriptRecursionException();
//                        }
//                    }
//                }
//                System.out.println(String.join(" ", command));
//                execCommandCode = commandExecutor.execute(command[0], command[1]);
//            } while (scriptScanner.hasNextLine() && execCommandCode == 0);
//            fir.setFileReadMode(false);
//            fir.setScanner(latestScanner);
//            if (execCommandCode == 1) throw new ErrorInScriptException();
//            if (execCommandCode == 2) throw new RecursionException();
//            execCode = 0;
//            System.out.println("Скрипт " + scriptName + " успешно выполнен!");
//        } catch (FileNotFoundException e) {
//            execCode = 1;
//            System.out.println("Файл не найден");
//        } catch (SecurityException e) {
//            execCode = 1;
//            System.out.println("Нет прав на чтение файла");
//        } catch (EmptyScriptException e) {
//            execCode = 1;
//            System.out.println("Файл скрипта пуст");
//        } catch (ScriptRecursionException e) {
//            execCode = 2;
//            System.out.println("В скрипте обнаружена рекурсия");
//        } catch (ErrorInScriptException e) {
//            execCode = 1;
//            System.out.println("Ошибка ввода команд в скрипте");
//        } catch (RecursionException e) {
//            execCode = 2;
//        } finally {
//            scriptStack.remove(scriptStack.size() - 1);
//        }
//        return execCode;
//    }
}

