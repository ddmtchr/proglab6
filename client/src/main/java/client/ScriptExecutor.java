package client;

import exceptions.*;
import input_change_later.CommandInputReceiver;
import utility.Request;
import utility.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ScriptExecutor {
    private ArrayList<String> scriptStack = new ArrayList<>();
    public int executeScript(String scriptName, ConnectionProvider connectionProvider) {
        int execCode;
        int execCommandCode = 0;
        try {
            Request request;
            scriptStack.add(scriptName);
            File scriptFile = new File(scriptName);
            if (!scriptFile.exists()) throw new FileNotFoundException();
            if (!scriptFile.canRead()) throw new SecurityException();
            Scanner scriptScanner = new Scanner(scriptFile);
            if (!scriptScanner.hasNextLine()) throw new EmptyScriptException();
            CommandInputReceiver cir = new CommandInputReceiver(scriptScanner);
//            Scanner latestScanner = fir.getScanner();
            cir.getFir().setFileReadMode(true);
//            fir.setScanner(scriptScanner);
            System.out.println("Выполение скрипта " + scriptName + "...");
            do {
                String[] command = cir.parseCommand();
                if (command[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (command[1].equals(script)) {
                            cir.getFir().setFileReadMode(false);
//                            fir.setScanner(latestScanner);
                            throw new ScriptRecursionException();
                        }
                    }
                    execCommandCode = executeScript(command[1], connectionProvider);

                } else if (command[0].equals("exit")) {
                    if (command[1].equals(" ")) {
                        System.out.println("Выход из программы...");
                        System.exit(0);
                    } else throw new ErrorInScriptException();
                }
                if (execCommandCode == 2) throw new RecursionException();

                System.out.println(String.join(" ", command));
                request = cir.pack(command);
                connectionProvider.send(request);
                Response response = connectionProvider.receive();

                if (response == null) {
                    System.out.println("Приносим искренние извинения, сервер двинул кони");
                    execCommandCode = 1;
                } else {
                    ResponseDecoder decoder = new ResponseDecoder();
                    execCommandCode = decoder.decode(response);
                }


            } while (scriptScanner.hasNextLine() && execCommandCode == 0);
            cir.getFir().setFileReadMode(false);
            if (execCommandCode == 1) throw new ErrorInScriptException();
            execCode = 0;
            System.out.println("Скрипт " + scriptName + " успешно выполнен!");

        } catch (FileNotFoundException e) {
            execCode = 1;
            System.out.println("Файл не найден");
        } catch (SecurityException e) {
            execCode = 1;
            System.out.println("Нет прав на чтение файла");
        } catch (EmptyScriptException e) {
            execCode = 1;
            System.out.println("Файл скрипта пуст");
        } catch (ScriptRecursionException e) {
            execCode = 2;
            System.out.println("В скрипте обнаружена рекурсия");
        } catch (NoSuchCommandException | ErrorInScriptException e) {
            execCode = 1;
            System.out.println("Ошибка ввода команд в скрипте");
        } catch (RecursionException e) {
            execCode = 2;
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return execCode;
    }
}
