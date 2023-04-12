package client;

import input_change_later.CommandInputReceiver;
import utility.Request;
import utility.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Client {
    private String hostName;
    private int port;
//    private DatagramChannel dc;
//    private SocketAddress isa;
    private Scanner scanner = new Scanner(System.in);
    private CommandInputReceiver cir;
    private ConnectionProvider connectionProvider;
    private static final String[] greetings = new String[]{
            "Как ваше ничего? ",
            "Добрый вечер. ",
            "Guten Tag! ",
            "Konnichiwa! ",
            "Храни Вас Господь. "
    };

    public Client(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    private void greet() {
        Random generator = new Random();
        int greetingNumber = generator.nextInt(greetings.length);
        System.out.println(greetings[greetingNumber] + "Введите help для справки.");
    }

//    private void openConnection(String hostName, int port) {
//        try {
//            dc = DatagramChannel.open();
//            isa = new InetSocketAddress(hostName, port);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private Request createRequest() {
//        try {
//            cir = new CommandInputReceiver(scanner);
//            fir = new FieldInputReceiver(scanner);
//            String[] command = cir.parseCommand();
//            if (cir.validateStringArgs(command)) {
//                Command cmd = cir.getCommandMap().get(command[0]);
//                LabWorkStatic lws = null;
//                if (cir.needObject(cmd)) {
//                    lws = fir.enterLabWork();
//                }
//                return new Request(cmd, command[1], lws);
//            }
//        } catch (ErrorInScriptException e) {
//
//        }
//        return null;
//    }

    private ByteBuffer packRequest(Request r) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
//        System.out.println(r.toString());
        oos.writeObject(r);
        ByteBuffer buffer = ByteBuffer.wrap(baos.toByteArray());
        return buffer;
    }

    public void run() {
        try {
            connectionProvider = new ConnectionProvider(hostName, port);
            greet();

            cir = new CommandInputReceiver(scanner);
            int processCode = 0;
            do {
                Request request = cir.createRequest(connectionProvider);

                // exec exit

                if (request != null) {
                    connectionProvider.send(request);

//                    System.out.println("Отправил запрос");
                    Response response = connectionProvider.receive();

//                    System.out.println("Принял ответ");

                    if (response == null) {
                        System.out.println("Приносим искренние извинения, сервер двинул кони");
                        continue;
                    }
                    ResponseDecoder decoder = new ResponseDecoder();
                    processCode = decoder.decode(response);

//                    System.out.println("Расшифровал ответ");
                }
            } while (true);

        } catch (IOException e) {
            System.out.println(">IOException<");
            e.printStackTrace();

        } catch (NoSuchElementException e) {
            System.out.println("Ввод завершен пользователем");
            System.exit(0);
        }
    }
}
