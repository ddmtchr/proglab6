import server.Server;

public class App {
    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
        server.run();

    }
}
