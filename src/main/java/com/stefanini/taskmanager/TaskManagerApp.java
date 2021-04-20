package com.stefanini.taskmanager;

import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;
import org.apache.log4j.BasicConfigurator;

import static com.stefanini.taskmanager.utils.ParamsExtractor.*;


public class TaskManagerApp {
    public static final TaskService taskService = new TaskServiceImpl();
    public static final UserService userService = new UserServiceImpl();

    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args) {
        for (String arg: args
             ) {
            System.out.println(arg);
        };
        if (args.length == 0) {
            throw new IllegalArgumentException("Error: no arguments were passed");
        }

        String task = "";

        try {
            task = getOperationName(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (task) {
            case CREATE_USER: {
                userService.createUser(args);
                break;
            }
            case SHOW_USERS:
                userService.getUsers();
                break;
            case CREATE_TASK: {
                taskService.createTask(args);
                break;
            }
            case SHOW_TASKS_BY_USERNAME: {
                taskService.getTasksByUsername(args);
                break;
            }
            case SHOW_ALL_TASKS: {
                taskService.getTasks(args);
                break;
            }
            case COMPLETE_TASK: {
                taskService.completeTask(args);
                break;
            }
        }
    }
}
