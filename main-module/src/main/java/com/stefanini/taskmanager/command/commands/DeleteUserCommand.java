package com.stefanini.taskmanager.command.commands;

import com.stefanini.taskmanager.command.Command;
import com.stefanini.taskmanager.command.CommandStore;

public class DeleteUserCommand implements Command{
    CommandStore commandStore;
    public DeleteUserCommand(CommandStore store) {
        this.commandStore = store;
    }

    @Override
    public void execute(String[] args) {
        commandStore.deleteUser(args);
    }
}
