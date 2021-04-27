package com.stefanini.taskmanager.command;

import com.stefanini.taskmanager.dao.factory.AbstractDAOFactory;
import com.stefanini.taskmanager.dao.factory.DAOFactory;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;


public class CommandStore {
    private static final DAOFactory daoFactory;
    private static final UserService userService;
    private static final TaskService taskService;

    static {
        daoFactory = AbstractDAOFactory.createDAOFactory("jdbc");
        taskService = new TaskServiceImpl(daoFactory.getTaskDAO());
        userService = new UserServiceImpl(daoFactory.getUserDAO());
    }

    public void createUser(String[] args) {
        userService.createUser(args);
    }

    public void getUsers() {
        userService.getList();
    }

    public void createTask(String[] args) {
        taskService.createTask(args);
    }

    public void getTasksByUsername(String[] args) {
        taskService.getTasksByUsername(args);
    }

    public void getTasks() {
        taskService.getList();
    }

    public void completeTask(String[] args) {
        taskService.completeTask(args);
    }

    public void deleteUser(String[] args){
        userService.delete(args);
    }
}
