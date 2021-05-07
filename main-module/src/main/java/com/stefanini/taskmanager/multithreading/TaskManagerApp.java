package com.stefanini.taskmanager.multithreading;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class TaskManagerApp {
    private static final Logger log = Logger.getLogger(TaskManagerApp.class);
    private static final String[] inputData = new String[5];

    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args) {
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(System.in))) {
            log.info("Enter username");
            inputData[0] = "-un='" + rd.readLine() + "'";
            log.info("Enter user's first name");
            inputData[1] = "-fn='" + rd.readLine() + "'";
            log.info("Enter user's last name");
            inputData[2] = "-ln='" + rd.readLine() + "'";
            log.info("Enter task title");
            inputData[3] = "-tt='" + rd.readLine() + "'";
            log.info("Enter task description");
            inputData[4] = "-td='" + rd.readLine() + "'";
        } catch (IOException e) {
            log.error("Error on introducing params: " + e.getMessage());
        }

        for (String str : inputData) {
            System.out.println(str);
        }

        ConcurrentCommandExecutionEnvironment env = new ConcurrentCommandExecutionEnvironment();
        env.readArgsAndExecuteCommand(inputData);
    }
}
