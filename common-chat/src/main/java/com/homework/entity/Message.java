package com.homework.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Serega on 06.11.2017.
 */
public class Message implements Serializable {

    private MessageType messageType;
    private User user;
    private String text;
    HashSet<String> usersEmailSet;
    private String receiverOfTheMessage;
    private String sanderOfMessage;

    public Message(MessageType messageType, User user, Set<String> usersEmailSet) {
        this.messageType = messageType;
        this.user = user;
        this.usersEmailSet = new HashSet<>(usersEmailSet);
    }

    public Message(MessageType messageType, User user, String text, Set<String> userEmail) {
        this.messageType = messageType;
        this.user = user;
        this.text = text;
        this.usersEmailSet = new HashSet<>(userEmail);
    }

    public Message(MessageType messageType, User user) {
        this.messageType = messageType;
        this.user = user;
    }

    public Message(MessageType messageType, User user, String text) {
        this.messageType = messageType;
        this.user = user;
        this.text = text;
    }

    public Message(MessageType messageType, User user, String text, String receiverOfTheMessage) {
        this.messageType = messageType;
        this.user = user;
        this.text = text;
        this.receiverOfTheMessage = receiverOfTheMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<String> getUsersEmailSet() {
        return usersEmailSet;
    }

    public void setUsersEmailSet(Set<String> usersEmailSet) {
        this.usersEmailSet = new HashSet<>(usersEmailSet);
    }

    public String getReceiver() {
        return receiverOfTheMessage;
    }

    public void setReceiverOfTheMessage(String receiverOfTheMessage) {
        this.receiverOfTheMessage = receiverOfTheMessage;
    }

    public String getSender() {
        return sanderOfMessage;
    }

    public void setSanderOfMessage(String sanderOfMessage) {
        this.sanderOfMessage = sanderOfMessage;
    }

    public Message(MessageType messageType, String text, String receiverOfTheMessage, String sanderOfMessage) {
        this.messageType = messageType;
        this.text = text;
        this.receiverOfTheMessage = receiverOfTheMessage;
        this.sanderOfMessage = sanderOfMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", user=" + user +
                ", text='" + text + '\'' +
                ", usersEmailSet=" + usersEmailSet +
                '}';
    }
}
