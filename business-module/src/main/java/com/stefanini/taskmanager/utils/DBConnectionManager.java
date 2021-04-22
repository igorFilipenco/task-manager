package com.stefanini.taskmanager.utils;

import com.stefanini.taskmanager.validation.PropertiesValidator;
import org.apache.log4j.Logger;

import javax.xml.bind.PropertyException;
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
        Properties properties = DBParamsConfig.getProperties();

        try {
            PropertiesValidator.validateDBProperties(properties);
        } catch (PropertyException e) {
            log.error(e.getMessage());
        }

        DB_URL = properties.getProperty("db.url");
        DB_USER_NAME = properties.getProperty("db.user");
        DB_USER_PASSWORD = properties.getProperty("db.password");
    }
}
