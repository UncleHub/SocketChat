package com.homework.controllers;

import com.homework.entity.Message;
import com.homework.entity.MessageType;
import com.homework.listener.ServerListener;
import com.homework.service.ChatWindowService;
import com.homework.utils.Context;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Serega on 05.11.2017.
 */
public class ChatWindowController {

    final static Logger logger = Logger.getLogger(ChatWindowController.class);


    public TabPane tabPanel;
    public ListView usersList;
    public Button sendButton;
    public TextField messageField;
    public Button openPrivateMessageTabButton;

    boolean listening = true;

    ChatWindowService chatWindowService = new ChatWindowService();

    HashMap<String, Tab> tabsMap = new HashMap<String, Tab>();

    public void initialize() {

        logger.info("initialize chat window");
        getNewTab("Main Chat");



        Thread thread = new Thread(() -> {
            ArrayBlockingQueue<Message> chatMessages = Context.getInstance().getChatMessages();
            Message message;

            try {
                while ((message = chatMessages.take()) != null && listening) {
                    final Message newMessage = message;
                    Platform.runLater(() -> processMessage(newMessage));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        usersList.getItems().addAll(Context.getInstance().getUsersEmailSet());
    }

    private void processMessage(Message message) {
        MessageType messageType = message.getMessageType();
        String email = message.getSanderUser().getEmail();
        switch (messageType) {
            case PUBLIC_MESSAGE: {

                handlePublicMessage(message);
                break;
            }
            case PRIVATE_MESSAGE: {

                handlePrivateMessageReceived(message);
                break;
            }
            case USER_CONNECTED: {

                usersList.getItems().add(email);
                handlePublicMessage(message);
                //TODO передалать на сервере сообщение
                break;

            }
            case USER_DISCONNECTED: {
                usersList.getItems().remove(email);
                tabPanel.getTabs().remove(tabsMap.get(email));
                //TODO проверить все потоки
                break;
            }
        }
    }

    private void handlePublicMessage(Message message) {
        Tab tab = tabsMap.get("Main Chat");
        addMessage(message, tab);
    }

    public void shutdown() {
        ServerListener serverListener = Context.getInstance().getServerListener();
        serverListener.shutdown();
        listening = false;
    }

    private void handlePrivateMessageReceived(Message message) {
        if (message.getSanderUser().getEmail().equals(Context.getInstance().getUser().getEmail())) {
            Tab tab = tabsMap.get(message.getReceiver());
            addMessage(message, tab);
        } else {
            if (tabsMap.containsKey(message.getSanderUser().getEmail())) {
                Tab tab = tabsMap.get(message.getSanderUser().getEmail());
                addMessage(message, tab);
            } else {
                Tab tab = openTab(message.getSanderUser().getEmail());
                addMessage(message, tab);
            }
        }
    }

    private void addMessage(Message message, Tab tab) {
        ListView<String> listView = getListView(tab);
        String text =message.getSanderUser().getEmail()+ " write: "+message.getText();
        listView.getItems().add(text);
    }

    public void sendMessage(ActionEvent actionEvent) {
        chatWindowService.sendMessage(messageField.getText(), tabPanel.getSelectionModel().getSelectedItem().getText());
        messageField.clear();
    }

    public void openPrivateDialog(ActionEvent actionEvent) {
        String email = ( String ) usersList.getSelectionModel().getSelectedItem();
        if (email != null) {
            openTab(email);
        }
    }

    private Tab openTab(String email) {

        if (tabsMap.containsKey(email)) {
            tabPanel.getSelectionModel().select(tabsMap.get(email));
            return tabsMap.get(email);
        } else {
            return getNewTab(email);
        }
    }

    public Tab getNewTab(String email) {

        Tab tab = new Tab(email, chatWindowService.getTabContent());
        //tab.setDisable(false);
        //tab.setClosable(true);
        tabPanel.getTabs().add(tab);
        tabsMap.put(email, tab);
        return tab;
    }

    private ListView<String> getListView(Tab tab) {
        AnchorPane tabContent = ( AnchorPane ) tab.getContent();
        ObservableList<Node> children = tabContent.getChildren();
        return ( ListView<String> ) children.get(0);
    }

}
