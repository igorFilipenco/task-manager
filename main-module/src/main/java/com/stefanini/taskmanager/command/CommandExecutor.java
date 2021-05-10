package com.stefanini.taskmanager.command;

public class CommandExecutor {
    Command createUser;
    Command getUserList;
    Command createTask;
    Command getTasks;
    Command getTasksByUsername;
    Command completeTask;
    Command deleteUser;
    Command createUserAndTask;
    Command assignTask;

    public CommandExecutor(
            Command createUser,
            Command getUserList,
            Command createTask,
            Command getTasks,
            Command getTasksByUsername,
            Command completeTask,
            Command deleteUser,
            Command createUserAndTask,
            Command assignTask
    ) {
        this.createUser = createUser;
        this.getUserList = getUserList;
        this.createTask = createTask;
        this.getTasks = getTasks;
        this.getTasksByUsername = getTasksByUsername;
        this.completeTask = completeTask;
        this.deleteUser = deleteUser;
        this.createUserAndTask = createUserAndTask;
        this.assignTask = assignTask;
    }

    public void createUser(String[] args){
        createUser.execute(args);
    }

    public void getUsers(String[] args){
        getUserList.execute(args);
    }

    public void createTask(String[] args){
        createTask.execute(args);
    }

    public void getTasks(String[] args){
        getTasks.execute(args);
    }

    public void getTasksByUsername(String[] args){
        getTasksByUsername.execute(args);
    }

    public void completeTask(String[] args){
        completeTask.execute(args);
    }

    public void deleteUser(String[] args) {
        deleteUser.execute(args);
    }

    public void createUserAndTask(String[] args) {createUserAndTask.execute(args);}

    public void assignTask(String[] args) {
        assignTask.execute(args);
    }
}
