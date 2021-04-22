package com.stefanini.taskmanager;

import com.stefanini.taskmanager.utils.CommandManager;
import org.apache.log4j.BasicConfigurator;


public class TaskManagerApp {
    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }

        if (args.length == 0) {
            throw new IllegalArgumentException("Error: no arguments were passed");
        }

        CommandManager manager = new CommandManager();
        manager.readArgsAndExecuteCommand(args);
    }
}
