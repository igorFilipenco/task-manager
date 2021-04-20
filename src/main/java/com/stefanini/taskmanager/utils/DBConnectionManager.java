package com.stefanini.taskmanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnectionManager {
    private final static String DB_URL= "jdbc:mysql://localhost:3306/test";
    //private final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_USER_NAME = "admin";
    private final static String DB_USER_PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL,DB_USER_NAME, DB_USER_PASSWORD);
    }
}
