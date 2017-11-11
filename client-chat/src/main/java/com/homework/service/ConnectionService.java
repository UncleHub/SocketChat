package com.homework.service;

import com.homework.entity.Message;
import com.homework.entity.MessageType;
import com.homework.entity.User;
import com.homework.listener.ServerListener;
import com.homework.utils.Context;

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


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public User login(User user) {

        try {
            Message message = new Message(MessageType.LOGINIZATION, user);
            outputStream.writeObject(message);
            Message receivedMessage = ( Message ) objectInputStream.readObject();
            receivedMessage.getUsersEmailSet().forEach(System.out::println);
            User receivedUser = receivedMessage.getUser();
            Context.getInstance().setUsersEmailSet(receivedMessage.getUsersEmailSet());
            ServerListener serverListener = new ServerListener(objectInputStream);
            Context.getInstance().setServerListener(serverListener);
            serverListener.start();
            return receivedUser;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
// TODO use log4j
    }

    public User signUp(User user) {

        try {
            Message message = new Message(MessageType.REGISTRATION, user);
            outputStream.writeObject(message);
            Message receivedMessage = ( Message ) objectInputStream.readObject();
            User receivedUser = receivedMessage.getUser();
            Context.getInstance().setUsersEmailSet(receivedMessage.getUsersEmailSet());
            ServerListener serverListener = new ServerListener(objectInputStream);
            Context.getInstance().setServerListener(serverListener);
            serverListener.start();
            return receivedUser;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

// TODO use log4j
    }

    public void publicMessage(String text) {
        Message message = new Message(MessageType.PUBLIC_MESSAGE, Context.getInstance().getUser(), text);
        try {
            outputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void privateMessage(String text, String tabName) {
        Message message = new Message(MessageType.PRIVATE_MESSAGE, Context.getInstance().getUser(), text, tabName);
    }
}
