package com.homework.service;

import com.homework.entity.User;
import com.homework.utils.Context;

/**
 * Created by Serega on 05.11.2017.
 */
public class AuthorizationService {

    //public static final String HOST = "127.0.0.1";
    public static final String HOST = "18.196.3.178";
    public static final int PORT = 6374;

    private ConnectionService getConnectionService() {
        ConnectionService connectionService = Context.getInstance().getConnectionService();
        connectionService.connect(HOST, PORT);
        return connectionService;
    }

    public User login(User user) {

        ConnectionService connectionService = getConnectionService();

        return connectionService.login(user);
    }

    public User signUpUser(User user) {
        ConnectionService connectionService = getConnectionService();

       return connectionService.signUp(user);
    }


}
