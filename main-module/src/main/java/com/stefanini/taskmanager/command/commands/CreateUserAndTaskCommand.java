package com.stefanini.taskmanager.command.commands;

import com.stefanini.taskmanager.command.Command;
import com.stefanini.taskmanager.command.CommandStore;


public class CreateUserAndTaskCommand implements Command {
    CommandStore commandStore;

    public CreateUserAndTaskCommand(CommandStore commandStore) {
        this.commandStore = commandStore;
    }

    @Override
    public void execute(String[] args) {
        commandStore.createUserAndTask(args);
    }
}
