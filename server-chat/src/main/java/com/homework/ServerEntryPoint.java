package com.homework;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Serega on 06.11.2017.
 */
public class ServerEntryPoint {

    static Map<String, ClientSocketThread> clientMap = new HashMap<String, ClientSocketThread>();

    final static Logger logger = Logger.getLogger(ServerEntryPoint.class);

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(6374);

            while (true) {
                logger.info("Waiting for new user");
                Socket socket = serverSocket.accept();
                logger.info("User has join");
                ClientSocketThread thread = new ClientSocketThread(socket,clientMap);
                thread.start();
              }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
