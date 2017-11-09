package com.homework.service;

import com.homework.entity.User;
import com.homework.repository.SqlRequest;

import java.sql.ResultSet;
import java.sql.SQLException;
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

        ResultSet select = sqlRequest.selectWithConditions(nameOfTable, columns, userMap);
        try {
            if (select.next()) {
                String email = select.getString("email");
                String password = select.getString("password");
                return new User(email, password);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

