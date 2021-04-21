package com.stefanini.taskmanager.utils;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnectionManager {
    private static final Logger log = Logger.getLogger(DBConnectionManager.class);
    private static String DB_URL;
    private static String DB_USER_NAME;
    private static String DB_USER_PASSWORD;
    private static Connection connection;

    static {
        initConnectionParams();
    }

    private DBConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_USER_PASSWORD);
        }

        return connection;
    }

    private static void initConnectionParams() {
        Properties properties = AppConfig.getProperties();

        if (!properties.isEmpty()) {
            DB_URL = properties.getProperty("db.url");
            DB_USER_NAME = properties.getProperty("db.user");
            DB_USER_PASSWORD = properties.getProperty("db.password");
        } else {
            log.error("Extracting params error: wrong database parameters in config file");
        }
    }
}
