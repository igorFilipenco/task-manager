package com.stefanini.taskmanager.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AppConfig {
    private static final String CONFIG_PATH = "src/main/resources/appConfig.properties";
    private static final Logger log = Logger.getLogger(AppConfig.class);

    public static Properties getProperties() {
        Properties props = new Properties();

        try (InputStream is = new FileInputStream(CONFIG_PATH)) {
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            log.error("Read config file: " + e.getMessage());
            e.printStackTrace();
        }

        return props;
    }
}
