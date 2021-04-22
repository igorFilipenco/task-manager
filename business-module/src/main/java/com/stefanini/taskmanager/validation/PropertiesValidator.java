package com.stefanini.taskmanager.validation;

import javax.xml.bind.PropertyException;
import java.util.Properties;

public class PropertiesValidator {
    public static void validateDBProperties(Properties props) throws PropertyException {
        if (!props.containsKey("db.url") || props.getProperty("db.url") == null) {
            throw new PropertyException("ERROR : application parameters aren't configure properly, problem with database url");
        }

        if (!props.containsKey("db.user") || props.getProperty("db.user") == null) {
            throw new PropertyException("ERROR : application parameters aren't configure properly, problem with database user");
        }

        if (!props.containsKey("db.password") || props.getProperty("db.password") == null) {
            throw new PropertyException("ERROR : application parameters aren't configure properly, problem with database user password");
        }
    }
}
