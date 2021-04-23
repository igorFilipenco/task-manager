package com.stefanini.taskmanager.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class DBParamsConfig {
    private static final String CONFIG_FILE_NAME = "appConfig.properties";
    private static final Logger log = Logger.getLogger(DBParamsConfig.class);

    public static Properties getProperties() {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try (InputStream stream = loader.getResourceAsStream(CONFIG_FILE_NAME)) {
            props.load(stream);
        } catch (IOException e) {
            log.error("Read config file: " + e.getMessage());
            e.printStackTrace();
        }

        return props;
    }
}
