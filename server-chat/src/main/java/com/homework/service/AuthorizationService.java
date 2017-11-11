package com.homework.service;

import com.homework.entity.User;
import com.homework.repository.SqlRequest;

import java.util.HashMap;

/**
 * Created by Serega on 06.11.2017.
 */
public class AuthorizationService {

    SqlRequest sqlRequest = new SqlRequest();
    String nameOfTable = "user";
    String columns = "*";

    public User login(User user) {

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());

        return sqlRequest.selectWithConditions(nameOfTable, columns, userMap);
    }

    public User register(User user) {

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", user.getEmail());
        userMap.put("password", user.getPassword());

        boolean insert = sqlRequest.insert(nameOfTable, userMap);

        if (insert) {
            return login(user);
        } else {
            return null;
        }
    }
}

