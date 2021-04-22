package com.stefanini.taskmanager.command.commands;

import com.stefanini.taskmanager.command.Command;
import com.stefanini.taskmanager.command.CommandStore;


public class CreateTaskCommand implements Command {
    private final CommandStore commandStore;

    public CreateTaskCommand(CommandStore commandStore) {
        this.commandStore = commandStore;
    }

    @Override
    public void execute(String[] args) {
        commandStore.createTask(args);
    }
}
