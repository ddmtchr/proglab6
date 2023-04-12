package server;

import exceptions.ConnectionOpenException;
import exceptions.ResponseSendingException;
import managers___ch.CollectionManager;
import managers___ch.FileManager;
import utility.Request;
import utility.Response;
import utility.ResponseBuilder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private int port;
//    private DatagramChannel dc;
//    private SocketAddress isa;
    private Scanner scanner = new Scanner(System.in);
    private ConnectionProvider connectionProvider;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try {
            initCollection();
            logger.log(Level.INFO, "Коллекция загружена из файла");
            connectionProvider = new ConnectionProvider(port);
            ServerCommandExecutor serverCommandExecutor = new ServerCommandExecutor(scanner);
//            openConnection(port);
//            ByteBuffer buf = ByteBuffer.allocate(8192);
            while (true) {
                if (System.in.available() > 0) {
                    String[] serverCommand = serverCommandExecutor.parseCommand();
                    serverCommandExecutor.execute(serverCommand);
                }

                Request request = connectionProvider.receive();
                if (request == null) continue;
                else logger.log(Level.INFO, "Запрос принят");


                RequestProcessor processor = new RequestProcessor();

                int execCode = processor.processRequest(request);

                if (execCode == 0) logger.log(Level.INFO, "Команда выполнена");
                else logger.log(Level.WARNING, "Ошибка выполнения команды");
                Response serverResponse = new Response(ResponseBuilder.getAndClear(), execCode);
                connectionProvider.send(serverResponse, request.getHost());

                logger.log(Level.INFO, "Ответ отправлен");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void openConnection(int port) throws ConnectionOpenException {
//        try {
//            dc = DatagramChannel.open();
//            isa = new InetSocketAddress(port);
//            dc.bind(isa);
//            System.out.println("Сервер запущен на порту " + port);
//        } catch (IOException e) {
//
//
//            throw new ConnectionOpenException();
//        } catch (IllegalArgumentException e) {
//
//
//            throw new ConnectionOpenException();
//        }
//    }

    private void initCollection() {
        FileManager fileManager = new FileManager();
        if (!fileManager.isFileValid()) System.exit(0);
        if (!fileManager.readFromFile()) System.exit(0);
        CollectionManager.setLastInitTime();
    }
}
