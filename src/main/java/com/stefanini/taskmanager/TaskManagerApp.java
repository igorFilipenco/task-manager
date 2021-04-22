package com.stefanini.taskmanager;

import com.stefanini.taskmanager.dao.DAOFactory;
import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.dao.jdbcdaoimpl.JDBCDAOFactoryImpl;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;
import org.apache.log4j.BasicConfigurator;

import static com.stefanini.taskmanager.utils.ParamsExtractor.*;


public class TaskManagerApp {
    static {
        BasicConfigurator.configure();
    }

    public static void main(String[] args) {
        for (String arg : args
        ) {
            System.out.println(arg);
        }
        ;
        if (args.length == 0) {
            throw new IllegalArgumentException("Error: no arguments were passed");
        }

        String task = "";

        try {
            task = getOperationName(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DAOFactory daoFactory = new JDBCDAOFactoryImpl();
        UserDAO userDAO = daoFactory.getUserDAO();
        TaskDAO taskDAO = daoFactory.getTaskDAO();
        TaskService taskService = new TaskServiceImpl(taskDAO);
        UserService userService = new UserServiceImpl(userDAO);

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
