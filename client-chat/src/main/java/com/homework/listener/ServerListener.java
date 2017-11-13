package com.homework.listener;

import com.homework.entity.Message;
import com.homework.utils.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Serega on 07.11.2017.
 */
public class ServerListener extends Thread {

    ObjectInputStream objectInputStream;

    public ServerListener(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    boolean listening = true;

    @Override
    public void run() {

        try {
            ArrayBlockingQueue<Message> chatMessagesQueue = Context.getInstance().getChatMessages();
            while (listening) {
                Message message = ( Message ) objectInputStream.readObject();
                chatMessagesQueue.add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {

        listening = false;

    }
}
