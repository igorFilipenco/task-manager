package com.stefanini.taskmanager.command.commands;

import com.stefanini.taskmanager.command.Command;
import com.stefanini.taskmanager.command.CommandStore;

public class GetTaskByUsernameCommand implements Command {
    CommandStore commandStore;

    public GetTaskByUsernameCommand(CommandStore commandStore) {
        this.commandStore = commandStore;
    }

    @Override
    public void execute(String[] args) {
        commandStore.getTasksByUsername(args);
    }
}
