package com.homework.service;

import com.homework.entity.Message;
import com.homework.entity.MessageType;
import com.homework.entity.User;
import com.homework.listener.ServerListener;
import com.homework.utils.Context;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Serega on 06.11.2017.
 */
public class ConnectionService {

    final static Logger logger = Logger.getLogger(ConnectionService.class);

    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream objectInputStream;

    public void connect(String host, int port) {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(host);
            clientSocket = new Socket(inetAddress, port);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            logger.info("start of connection with server");

        } catch (UnknownHostException e) {
            logger.error("connection with server denied",e);
        } catch (IOException e) {
            logger.error("connection with server denied",e);
        }
    }

    public User login(User user) {

        try {
            Message message = new Message(MessageType.LOGINIZATION, user);
            outputStream.writeObject(message);
            Message receivedMessage = ( Message ) objectInputStream.readObject();
            logger.info("received message from server, while login\n"+receivedMessage.toString());
            User receivedUser = receivedMessage.getSanderUser();
            Context.getInstance().setUsersEmailSet(receivedMessage.getUsersEmailSet());
            ServerListener serverListener = new ServerListener(objectInputStream);
            Context.getInstance().setServerListener(serverListener);
            serverListener.start();
            return receivedUser;

        } catch (IOException e) {
            logger.error("login failed",e);
            return null;
        } catch (ClassNotFoundException e) {
            logger.error("login failed",e);
            return null;
        }
    }

    public User signUp(User user) {

        try {
            Message message = new Message(MessageType.REGISTRATION, user);
            outputStream.writeObject(message);
            Message receivedMessage = ( Message ) objectInputStream.readObject();

            logger.info("received message from server, while sign in\n"+receivedMessage.toString());
            User receivedUser = receivedMessage.getSanderUser();
            Context.getInstance().setUsersEmailSet(receivedMessage.getUsersEmailSet());
            ServerListener serverListener = new ServerListener(objectInputStream);
            Context.getInstance().setServerListener(serverListener);
            serverListener.start();
            return receivedUser;

        } catch (IOException e) {
            logger.error("sign up failed",e);
            return null;
        } catch (ClassNotFoundException e) {
            logger.error("sign up failed",e);
            return null;
        }
    }

    public void publicMessage(String text) {
        Message message = new Message(MessageType.PUBLIC_MESSAGE, Context.getInstance().getUser(), text);
        try {
            outputStream.writeObject(message);
            logger.info("public message sand");
        } catch (IOException e) {
            logger.error("public message failed",e);
        }
    }

    public void privateMessage(String text, String tabName) {
        Message message = new Message(MessageType.PRIVATE_MESSAGE, Context.getInstance().getUser(), text, tabName);
        try {
            outputStream.writeObject(message);
            logger.info("private message sand to "+tabName);
        } catch (IOException e) {
            logger.error("private message to "+tabName+" failed",e);

        }
    }
}
