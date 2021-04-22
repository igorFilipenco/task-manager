package com.stefanini.taskmanager.command.commands;

import com.stefanini.taskmanager.command.Command;
import com.stefanini.taskmanager.command.CommandStore;


public class GetTasksListCommand implements Command {
    CommandStore commandStore;

    public GetTasksListCommand(CommandStore commandStore) {
        this.commandStore = commandStore;
    }

    @Override
    public void execute(String[] args) {
        commandStore.getTasks();
    }
}
