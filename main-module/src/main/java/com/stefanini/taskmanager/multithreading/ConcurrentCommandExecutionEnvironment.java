package com.stefanini.taskmanager.multithreading;

import com.stefanini.taskmanager.command.CommandExecutor;
import com.stefanini.taskmanager.command.CommandStore;
import com.stefanini.taskmanager.command.commands.*;
import com.stefanini.taskmanager.multithreading.threadexecutor.ThreadExecutor;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConcurrentCommandExecutionEnvironment {
    private static final Logger log = Logger.getLogger(ConcurrentCommandExecutionEnvironment.class);

    public void readArgsAndExecuteCommand(String[] props) {
        if (props.length == 0) {
            throw new IllegalArgumentException("Error: no params entered");
        }

        CommandStore commandStore = new CommandStore();
        CommandExecutor commandExecutor = new CommandExecutor(
                new CreateUserCommand(commandStore),
                new GetUserListCommand(commandStore),
                new CreateTaskCommand(commandStore),
                new GetTasksListCommand(commandStore),
                new GetTaskByUsernameCommand(commandStore),
                new CompleteTaskCommand(commandStore),
                new DeleteUserCommand(commandStore),
                new CreateUserAndTaskCommand(commandStore),
                new AssignTaskCommand(commandStore)
        );

        List<Runnable> tasks = new ArrayList<>(Arrays.asList(
                () -> commandExecutor.createUser(props),
                () -> commandExecutor.createTask(props),
                () -> commandExecutor.assignTask(props),
                () -> commandExecutor.getUsers(props),
                () -> commandExecutor.getTasks(props)
        ));

        ThreadExecutor threadExecutor = new ThreadExecutor(tasks.size(), tasks);
        threadExecutor.executeTasksOneByOne();
    }
}
