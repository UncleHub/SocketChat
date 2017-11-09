package com.homework.controllers;

import com.homework.entity.Message;
import com.homework.entity.User;
import com.homework.service.ChatWindowService;
import com.homework.utils.Context;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Serega on 05.11.2017.
 */
public class ChatWindowController {
    public Button sendButton;
    public TextField messageField;
    public ListView usersList;
    public Button privateMessageButton;
    public AnchorPane mainTab;
    public ListView chatList;
    public Tab mainChatTab;
    public TabPane tabPanel;
    public ListView listOfUser;

    ChatWindowService chatWindowService = new ChatWindowService();


    public void initialize() {

        Thread thread = new Thread(() -> {
            ArrayBlockingQueue<Message> chatMessages = Context.getInstance().getChatMessages();
            Message message;
            try {
                while ((message = chatMessages.take()) != null) {
                    System.out.println("message ="+ message.toString());
                    chatList.getItems().add(message.getUser().getEmail() + " write: " + message.getText());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        //listOfUser.addAll(chatWindowService.getUserList());
        //usersList.setItems(listOfUser);

    }


    public void sendMessage(ActionEvent actionEvent) {

        chatWindowService.sendMessage(messageField.getText());
        messageField.clear();

    }

    public void openPrivateDialog(ActionEvent actionEvent) {
        User selectedUser = ( User ) usersList.getSelectionModel().getSelectedItem();
        chatWindowService.privateMessage(selectedUser.getEmail());
        //не придумал пока как

    }
}
