package com.homework.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Serega on 06.11.2017.
 */
public class Message implements Serializable {

    private MessageType messageType;
    private User sanderUser;
    private String text;
    HashSet<String> usersEmailSet;
    private String receiverOfTheMessage;

    public Message(MessageType messageType, User user, Set<String> usersEmailSet) {
        this.messageType = messageType;
        this.sanderUser = user;
        this.usersEmailSet = new HashSet<>(usersEmailSet);
    }

    public Message(MessageType messageType, User user) {
        this.messageType = messageType;
        this.sanderUser = user;
    }

    public Message(MessageType messageType, User user, String text) {
        this.messageType = messageType;
        this.sanderUser = user;
        this.text = text;
    }

    public Message(MessageType messageType, User user, String text, String receiverOfTheMessage) {
        this.messageType = messageType;
        this.sanderUser = user;
        this.text = text;
        this.receiverOfTheMessage = receiverOfTheMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public User getSanderUser() {
        return sanderUser;
    }

    public void setSanderUser(User sanderUser) {
        this.sanderUser = sanderUser;
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

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", sanderUser=" + sanderUser +
                ", text='" + text + '\'' +
                ", usersEmailSet=" + usersEmailSet +
                ", receiverOfTheMessage='" + receiverOfTheMessage + '\'' +
                '}';
    }
}
