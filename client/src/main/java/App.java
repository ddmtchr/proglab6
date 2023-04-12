import client.Client;

public class App {
    public static void main(String[] args) {
        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.run();
    }
}
