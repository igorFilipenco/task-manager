package com.stefanini.taskmanager.utils;

import com.stefanini.taskmanager.command.CommandExecutor;
import com.stefanini.taskmanager.command.CommandStore;
import com.stefanini.taskmanager.command.commands.*;

import static com.stefanini.taskmanager.utils.ParamsExtractor.*;


public class CommandExecutionEnvironment {
    public void readArgsAndExecuteCommand(String[] args) {
        String task = "";

        try {
            task = getOperationName(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
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

        switch (task) {
            case CREATE_USER: {
                commandExecutor.createUser(args);
                break;
            }
            case SHOW_USERS:
                commandExecutor.getUsers(args);
                break;
            case CREATE_TASK: {
                commandExecutor.createTask(args);
                break;
            }
            case SHOW_TASKS_BY_USERNAME: {
                commandExecutor.getTasksByUsername(args);
                break;
            }
            case SHOW_ALL_TASKS: {
                commandExecutor.getTasks(args);
                break;
            }
            case COMPLETE_TASK: {
                commandExecutor.completeTask(args);
                break;
            }
            case DELETE_USER: {
                commandExecutor.deleteUser(args);
                break;
            }
            case CREATE_USER_AND_TASK:{
                commandExecutor.createUserAndTask(args);
            }
            //TODO create command for task deletion
        }
    }
}
