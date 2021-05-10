package com.stefanini.taskmanager.command;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.dao.factory.AbstractDAOFactory;
import com.stefanini.taskmanager.dao.factory.DAOFactory;
import com.stefanini.taskmanager.dao.factory.FactoryType;
import com.stefanini.taskmanager.service.TaskService;
import com.stefanini.taskmanager.service.TaskServiceImpl;
import com.stefanini.taskmanager.service.UserService;
import com.stefanini.taskmanager.service.UserServiceImpl;


public class CommandStore {
    private static final DAOFactory daoFactory;
    private static final UserService userService;
    private static final TaskService taskService;

    static {
        daoFactory = AbstractDAOFactory.createDAOFactory(FactoryType.HIBERNATE);
        UserDAO userDAO = daoFactory.getUserDAO();
        TaskDAO taskDAO = daoFactory.getTaskDAO();
        taskService = new TaskServiceImpl(taskDAO, userDAO);
        userService = new UserServiceImpl(taskDAO, userDAO);
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

    public void deleteUser(String[] args) {
        userService.delete(args);
    }

    public void createUserAndTask(String[] args) {
        userService.createUserAndAssignTask(args);
    }

    public void assignTask(String[] args) {
        taskService.assignTask(args);
    }
}
