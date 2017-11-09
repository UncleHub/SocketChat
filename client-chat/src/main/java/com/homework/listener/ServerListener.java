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
        this.objectInputStream=objectInputStream;
    }

    @Override
    public void run() {

        try {
            ArrayBlockingQueue<Message> chatMessages = Context.getInstance().getChatMessages();

            while (true) {

                Message message = ( Message ) objectInputStream.readObject();

                switch (message.getMessageType()){
                    case PUBLIC_MESSAGE:
                        chatMessages.add(message);
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
