package com.stefanini.taskmanager.command.commands;

import com.stefanini.taskmanager.command.Command;
import com.stefanini.taskmanager.command.CommandStore;

public class CompleteTaskCommand implements Command {
    CommandStore commandStore;

    public CompleteTaskCommand(CommandStore commandStore) {
        this.commandStore = commandStore;
    }

    @Override
    public void execute(String[] args) {
        commandStore.completeTask(args);
    }
}
