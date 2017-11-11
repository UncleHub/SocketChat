package com.homework.utils;

import com.homework.entity.Message;
import com.homework.entity.User;
import com.homework.listener.ServerListener;
import com.homework.service.ConnectionService;

import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Serega on 05.11.2017.
 */
public class Context {

    static Context instance;
    private User user;
    private ServerListener serverListener;


    private Set<String> usersEmailSet;

    ConnectionService connectionService = new ConnectionService();


    private ArrayBlockingQueue<Message> chatMessages = new ArrayBlockingQueue<Message>(1);

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

    public Set<String> getUsersEmailSet() {
        return usersEmailSet;
    }

    public void setUsersEmailSet(Set<String> usersEmailSet) {
        this.usersEmailSet = usersEmailSet;
    }

    public ServerListener getServerListener() {
        return serverListener;
    }

    public void setServerListener(ServerListener serverListener) {
        this.serverListener = serverListener;
    }
}
