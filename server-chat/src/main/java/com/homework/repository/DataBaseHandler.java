package com.homework.repository;

import java.sql.*;

/**
 * Created by Serega on 06.11.2017.
 */
public class DataBaseHandler {


    Connection connection;
    Statement statement;


    static DataBaseHandler instance;

    private DataBaseHandler() {
    }

    public static DataBaseHandler getInstance() {
        if (instance == null) {
            instance = new DataBaseHandler();
        }
        return instance;
    }

    public void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        instance.connection = DriverManager.getConnection("jdbc:sqlite:server-chat/dataBase/UsersDataBase.sqlite");
        instance.statement = instance.connection.createStatement();
        System.out.println("You have join to data base of users.");
    }

    public void close() throws SQLException {
        instance.statement.close();
        instance.connection.close();
        System.out.println("Connection with data base close");
    }

    public void createTables() throws SQLException {
        instance.statement.executeUpdate("DROP TABLE IF EXISTS user");
        instance.statement.executeUpdate("CREATE TABLE user(userId INTEGER PRIMARY KEY AUTOINCREMENT, email STRING UNIQUE, password STRING)");
    }


    public boolean insert(String sql) {
        try {
            instance.statement.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet select(String sql) {
        try {
            return instance.statement.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
    }

}
