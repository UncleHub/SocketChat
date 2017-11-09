import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serega on 06.11.2017.
 */
public class ServerEntryPoint {

    static Map<String, ClientSocketThread> clientMap = new HashMap<String, ClientSocketThread>();

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6374);

            while (true) {
                System.out.println("Waiting for new user");
                Socket socket = serverSocket.accept();
                System.out.println("User has join");
                ClientSocketThread thread = new ClientSocketThread(socket,clientMap);
                thread.start();
              }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
