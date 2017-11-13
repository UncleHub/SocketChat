package com.homework.service;

import com.homework.utils.Context;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

/**
 * Created by Serega on 07.11.2017.
 */
public class ChatWindowService {

    public void sendMessage(String text, String tabName) {
        ConnectionService connectionService = Context.getInstance().getConnectionService();
        if (tabName.equals("Main Chat")) {
            connectionService.publicMessage(text);
        } else{
            connectionService.privateMessage(text,tabName);
        }
    }

    public Node getTabContent() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("privateMessageTab.fxml"));
        try {
            return ( Node ) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
