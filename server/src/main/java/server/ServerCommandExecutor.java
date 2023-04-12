package server;

import managers___ch.CollectionManager;
import managers___ch.FileManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerCommandExecutor {

    private Scanner scanner;
    private static final Logger logger = Logger.getLogger(ServerCommandExecutor.class.getName());

    public ServerCommandExecutor(Scanner s) {
        scanner = s;
    }

    public String[] parseCommand() {
        String input = scanner.nextLine().trim();
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

    public void execute(String[] serverCommand) {
        if (serverCommand[0].equals("save")) {
            if (serverCommand[1].equals(" ")) {
                FileManager fileManager = new FileManager();
                if (fileManager.writeToFile()) {
                    logger.log(Level.INFO, "Коллекция сохранена в файл");
//                    System.out.println("Коллекция успешно сохранена в файл");
                    CollectionManager.setLastSaveTime();
                }
            } else {
                System.out.println("Использование: save");
            }
        } else if (serverCommand[0].equals("exit")) {
            if (serverCommand[1].equals(" ")) {
                logger.log(Level.INFO, "Выход с сервера");
                System.out.println("Роняем сервер...");
                System.exit(0);
            } else {
                System.out.println("Использование: exit");
            }
        } else {
            System.out.println("Неизвестная команда " + serverCommand[0]);
        }
    }
}
