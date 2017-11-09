package com.homework.utils;

import com.homework.entity.Message;
import com.homework.entity.User;
import com.homework.service.ConnectionService;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Serega on 05.11.2017.
 */
public class Context {

    static Context instance;
    private User user;

    ConnectionService connectionService = new ConnectionService();


    ArrayBlockingQueue<Message> chatMessages = new ArrayBlockingQueue<Message>(1);

    public ArrayBlockingQueue<Message> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayBlockingQueue<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public ConnectionService getConnectionService() {
        return connectionService;
    }

    public void setConnectionService(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
}
