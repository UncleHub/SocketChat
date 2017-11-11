package com.homework.entity;

import java.io.Serializable;

/**
 * Created by Serega on 06.11.2017.
 */
public enum MessageType implements Serializable{
    PRIVATE_MESSAGE,
    PUBLIC_MESSAGE,
    LOGINIZATION,
    REGISTRATION,
    USER_DISCONNECTED,
    USER_CONNECTED
}
