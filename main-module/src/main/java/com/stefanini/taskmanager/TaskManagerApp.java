package com.stefanini.taskmanager;

import com.stefanini.taskmanager.utils.CommandExecutionEnvironment;
import org.apache.log4j.BasicConfigurator;


public class TaskManagerApp {
    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Error: no arguments were passed");
        }

        CommandExecutionEnvironment environment = new CommandExecutionEnvironment();
        environment.readArgsAndExecuteCommand(args);
    }
}
