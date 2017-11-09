package com.homework.service;

import com.homework.utils.Context;

import java.util.LinkedList;

/**
 * Created by Serega on 07.11.2017.
 */
public class ChatWindowService {


    public LinkedList getUserList() {
        return null;
    }

    public void sendMessage(String text) {

        ConnectionService connectionService = Context.getInstance().getConnectionService();
        connectionService.publicMessage(text);


    }

    public void privateMessage(String email) {

    }
}
