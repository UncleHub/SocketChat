import com.homework.entity.Message;
import com.homework.entity.MessageType;
import com.homework.entity.User;
import com.homework.service.AuthorizationService;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Serega on 06.11.2017.
 */
public class ClientSocketThread extends Thread {

    final static Logger logger = Logger.getLogger(ClientSocketThread.class);

    private Socket socket;
    private ObjectOutputStream outputStream;

    public Socket getSocket() {
        return socket;
    }

    static Map<String, ClientSocketThread> clientMap = new HashMap<String, ClientSocketThread>();

    public ClientSocketThread(Socket socket, Map<String, ClientSocketThread> clientMap) {
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
                User messageUser = inputMessage.getSanderUser();
                logger.info("received message"+ inputMessage.toString());
                switch (messageType) {
                    case USER_DISCONNECTED: {
                        Message message = new Message(MessageType.USER_DISCONNECTED, messageUser, messageUser.getEmail() + " has disconnected");
                        clientMap.remove(messageUser.getEmail());
                        for (Map.Entry<String, ClientSocketThread> stringClientSocketThreadEntry : clientMap.entrySet()) {
                            ClientSocketThread socketThread = stringClientSocketThreadEntry.getValue();
                            ObjectOutputStream userObjectOutputStream = socketThread.getOutputStream();
                            logger.info(message.getSanderUser().getEmail()+" user disconnect");
                            userObjectOutputStream.writeObject(message);
                        }
                            streaming = false;
                        break;
                    }
                    case LOGINIZATION: {
                        User user = messageUser;
                        AuthorizationService authorizationService = new AuthorizationService();
                        User login = authorizationService.login(user);
                        sendConnectionMessage(login);
                        outputStream.writeObject(new Message(MessageType.LOGINIZATION, login, getUsersEmailSet()));
                        clientMap.put(login.getEmail(), this);
                        logger.info(user.getEmail() + " user has join");
                        break;
                    }
                    case REGISTRATION: {
                        User user = messageUser;
                        AuthorizationService authorizationService = new AuthorizationService();
                        User register = authorizationService.register(user);
                        sendConnectionMessage(register);
                        outputStream.writeObject(new Message(MessageType.REGISTRATION, register, getUsersEmailSet()));
                        clientMap.put(register.getEmail(), this);
                        logger.info(user.getEmail()+" has registrated and join");
                        break;
                    }
                    case PUBLIC_MESSAGE: {
                        logger.info("public message received");
                        handlePublicMessage(inputMessage);
                        break;
                    }
                    case PRIVATE_MESSAGE: {
                        String emailOfSander = inputMessage.getSanderUser().getEmail();
                        String emailOfReceiver = inputMessage.getReceiver();

                        logger.info(emailOfSander+" write private message to "+emailOfReceiver);
                        Message message = new Message(MessageType.PRIVATE_MESSAGE, inputMessage.getSanderUser(),inputMessage.getText(), emailOfReceiver);

                        writeMessage(emailOfSander, message);
                        writeMessage(emailOfReceiver, message);

                        break;
                    }
                }
            }

        } catch (IOException e) {
          logger.error("ClientSocketTread ",e);
        } catch (ClassNotFoundException e) {
            logger.error("ClientSocketTread ",e);
        }


    }

    private void handlePublicMessage(Message inputMessage) throws IOException {
        String text = inputMessage.getText();
        Message publicMessage = new Message(MessageType.PUBLIC_MESSAGE, inputMessage.getSanderUser(), text);
        for (Map.Entry<String, ClientSocketThread> stringClientSocketThreadEntry : clientMap.entrySet()) {
            ClientSocketThread socketThread = stringClientSocketThreadEntry.getValue();
            ObjectOutputStream userObjectOutputStream = socketThread.getOutputStream();
            System.out.println("message send" + publicMessage.toString());
            userObjectOutputStream.writeObject(publicMessage);
        }
        logger.info(inputMessage.getSanderUser().getEmail()+" write public message");
    }

    private void sendConnectionMessage(User user) {
        Message message = new Message(MessageType.USER_CONNECTED, user, user.getEmail() + " has join");
        for (Map.Entry<String, ClientSocketThread> stringClientSocketThreadEntry : clientMap.entrySet()) {
            ClientSocketThread socketThread = stringClientSocketThreadEntry.getValue();
            ObjectOutputStream userObjectOutputStream = socketThread.getOutputStream();
            message.setUsersEmailSet(getUsersEmailSet());
            try {
                userObjectOutputStream.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeMessage(String emailOfSander, Message message) throws IOException {
        ClientSocketThread sanderSocketThread = clientMap.get(emailOfSander);
        ObjectOutputStream sanderStream = sanderSocketThread.getOutputStream();
        sanderStream.writeObject(message);
    }

    private Set<String> getUsersEmailSet() {

        return clientMap.keySet();
    }

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
