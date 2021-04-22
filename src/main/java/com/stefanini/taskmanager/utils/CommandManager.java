package com.stefanini.taskmanager.utils;

import com.stefanini.taskmanager.dao.DAOFactory;
import com.stefanini.taskmanager.dao.jdbcdaoimpl.JDBCDAOFactoryImpl;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;

import static com.stefanini.taskmanager.utils.ParamsExtractor.*;


public class CommandManager {
    private final DAOFactory daoFactory = new JDBCDAOFactoryImpl();

    public void readArgsAndExecuteCommand(String[] args) {
        String task = "";

        try {
            task = getOperationName(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TaskService taskService = new TaskServiceImpl(daoFactory.getTaskDAO());
        UserService userService = new UserServiceImpl(daoFactory.getUserDAO());

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
