package com.homework.entity;

import java.io.Serializable;

/**
 * Created by Serega on 06.11.2017.
 */
public class Message implements Serializable{

    private MessageType messageType;
    private User user;
    private String text;


    public Message(MessageType messageType, User user) {
        this.messageType = messageType;
        this.user = user;
    }

    public Message(MessageType messageType, User user, String text) {
        this.messageType = messageType;
        this.user = user;
        this.text = text;
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

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }
}
