import com.homework.entity.Message;
import com.homework.entity.MessageType;
import com.homework.entity.User;
import com.homework.repository.DataBaseHandler;
import com.homework.service.AuthorizationService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serega on 06.11.2017.
 */
public class ClientSocketThread extends Thread {

    private Socket socket;
    private ObjectOutputStream outputStream;

    public Socket getSocket() {
        return socket;
    }

    static Map<String, ClientSocketThread> clientMap = new HashMap<String, ClientSocketThread>();

    public ClientSocketThread(Socket socket, Map <String,ClientSocketThread>clientMap) {
        this.socket = socket;
        this.clientMap = clientMap;
    }


    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void run() {

        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            boolean streaming = true;

            while (streaming) {

                Message inputMessage = ( Message ) objectInputStream.readObject();
                MessageType messageType = inputMessage.getMessageType();
                switch (messageType) {
                    case EXIT: {
                        streaming = false;
                        break;
                    }
                    case LOGINIZATION: {
                        User user = inputMessage.getUser();
                        AuthorizationService authorizationService = new AuthorizationService();
                        User login = authorizationService.login(user);
                        outputStream.writeObject(new Message(MessageType.LOGINIZATION, login));
                        clientMap.put(login.getEmail(), this);
                        DataBaseHandler.getInstance().close();
                        break;
                    }
                    case REGISTRATION: {
                        User user = inputMessage.getUser();
                        AuthorizationService authorizationService = new AuthorizationService();
                        User register = authorizationService.register(user);
                        outputStream.writeObject(new Message(MessageType.LOGINIZATION, register));
                        clientMap.put(register.getEmail(), this);
                        DataBaseHandler.getInstance().close();
                        break;
                    }
                    case PUBLIC_MESSAGE: {
                        System.out.println("message received");
                        Message publicMessage = new Message(MessageType.PUBLIC_MESSAGE, inputMessage.getUser(), inputMessage.getText());
                        for (Map.Entry<String, ClientSocketThread> stringClientSocketThreadEntry : clientMap.entrySet()) {
                            ClientSocketThread socketThread = stringClientSocketThreadEntry.getValue();
                            ObjectOutputStream userObjectOutputStream = socketThread.getOutputStream();
                            System.out.println("message send" + publicMessage.toString());
                            userObjectOutputStream.writeObject(publicMessage);
                        }

                        break;
                    }
                    case PRIVATE_MESSAGE: {

                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
