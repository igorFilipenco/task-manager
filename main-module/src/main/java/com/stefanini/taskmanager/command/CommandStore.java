package com.stefanini.taskmanager.command;

import com.stefanini.taskmanager.dao.DAOFactory;
import com.stefanini.taskmanager.dao.jdbcdaoimpl.JDBCDAOFactoryImpl;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;


public class CommandStore {
    private static final DAOFactory daoFactory = new JDBCDAOFactoryImpl();
    private static final UserService userService;
    private static final TaskService taskService;

    static {
        taskService = new TaskServiceImpl(daoFactory.getTaskDAO());
        userService = new UserServiceImpl(daoFactory.getUserDAO());
    }

    public void createUser(String[] args) {
        userService.createUser(args);
    }

    public void getUsers() {
        userService.getUsers();
    }

    public void createTask(String[] args) {
        taskService.createTask(args);
    }

    public void getTasksByUsername(String[] args) {
        taskService.getTasksByUsername(args);
    }

    public void getTasks() {
        taskService.getTasks();
    }

    public void completeTask(String[] args) {
        taskService.completeTask(args);
    }
}
