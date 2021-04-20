package com.stefanini.taskmanager.command;

public class CommandExecutor {
    Command createUser;

    public CommandExecutor(Command createUser) {
        this.createUser = createUser;
    }

    public void createUser() {
        createUser.execute();
    }
}
