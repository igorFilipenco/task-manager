package com.stefanini.taskmanager.command.commands;

import com.stefanini.taskmanager.command.Command;
import com.stefanini.taskmanager.command.CommandStore;

public class CreateUserCommand implements Command {
    CommandStore commandStore;

    public CreateUserCommand(CommandStore commandStore) {
        this.commandStore = commandStore;
    }

    @Override
    public void execute(String[] args) {
        commandStore.createUser(args);
    }
}
