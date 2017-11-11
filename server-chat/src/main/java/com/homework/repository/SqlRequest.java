package com.homework.repository;

import com.homework.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class SqlRequest {

    DataBaseHandler dataBase = DataBaseHandler.getInstance();

    public User selectWithConditions(String tableName, String columns, HashMap<String, Object> map) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        StringJoiner allCondition = new StringJoiner(" AND ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner(" = '", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlSelect = "SELECT " + columns + " FROM " + tableName + " WHERE " + allCondition + ";";

        try {
            dataBase.createConnection();
            ResultSet select = dataBase.select(sqlSelect);
            return new User(select.getString("email"), select.getString("password"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDataBase();
        }
        return null;
    }

    public boolean insert(String tableName, HashMap<String, Object> map) {

        Set<Map.Entry<String, Object>> entries = map.entrySet();

        StringJoiner columnsLabels = new StringJoiner(",");
        StringJoiner values = new StringJoiner("','", "'", "'");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            columnsLabels.add(oneOfItem.getKey());
            values.add(oneOfItem.getValue().toString());
        }
        String sqlInsert = "INSERT INTO " + tableName + " (" + columnsLabels + ") VALUES (" + values + ");";
        try {
            dataBase.createConnection();
            return dataBase.insert(sqlInsert);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDataBase();
        }
        return false;
    }

    private void closeDataBase() {
        try {
            dataBase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean update(String tableName, String whereCondition, HashMap<String, Object> map) {

        boolean insert = false;

        Set<Map.Entry<String, Object>> entries = map.entrySet();

        StringJoiner allCondition = new StringJoiner(", ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlUpdate = "UPDATE " + tableName + " SET " + allCondition + " WHERE idProduct =" + whereCondition + ";";

        try {

            dataBase.createConnection();
            insert = dataBase.insert(sqlUpdate);
            dataBase.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insert;
    }

    public boolean delete(String tableName, HashMap<String, Object> map) {

        boolean insert = false;

        Set<Map.Entry<String, Object>> entries = map.entrySet();

        StringJoiner allCondition = new StringJoiner("AND ");
        for (Map.Entry<String, Object> oneOfItem : entries) {
            StringJoiner condition = new StringJoiner("='", "", "'");
            condition.add(oneOfItem.getKey());
            condition.add(oneOfItem.getValue().toString());
            allCondition.add(condition.toString());
        }
        String sqlDelete = "DELETE FROM " + tableName + " WHERE " + allCondition + ";";
        try {
            dataBase.createConnection();
            insert = dataBase.insert(sqlDelete);
            dataBase.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insert;
    }

    public ResultSet selectAll(String tableName) {

        String sqlSelect = "SELECT * FROM " + tableName + ";";

        return dataBase.select(sqlSelect);
    }
}
