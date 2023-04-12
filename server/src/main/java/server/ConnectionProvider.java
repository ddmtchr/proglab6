package server;

import utility.Request;
import utility.Response;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ConnectionProvider {
    private final DatagramSocket socket;

    public ConnectionProvider(int port) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();

        socket = datagramChannel.socket();
        socket.setSoTimeout(100);
        socket.bind(new InetSocketAddress(port));
        System.out.println("Сервер запущен. Порт: " + port);
    }

    public void send(Response response, SocketAddress address) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(response);
            DatagramPacket responsePacket = new DatagramPacket(baos.toByteArray(), baos.size());
            responsePacket.setSocketAddress(address);
            socket.send(responsePacket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request receive() {
        Request request;
        try {
            ByteBuffer buf = ByteBuffer.allocate(8192);
            DatagramPacket datagramPacket = new DatagramPacket(buf.array(), buf.array().length);

            socket.receive(datagramPacket);
            SocketAddress address = datagramPacket.getSocketAddress();


            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buf.array()));

            request = (Request) objectInputStream.readObject();
            request.setHost(address);


            return request;

        } catch (IOException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
