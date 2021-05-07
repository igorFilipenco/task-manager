package com.stefanini.taskmanager.multithreading;

import com.stefanini.taskmanager.command.CommandExecutor;
import com.stefanini.taskmanager.command.CommandStore;
import com.stefanini.taskmanager.command.commands.*;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ConcurrentCommandExecutionEnvironment {
    private static final int POOL_SIZE = 4;

    private static final Logger log = Logger.getLogger(ConcurrentCommandExecutionEnvironment.class);

    public void readArgsAndExecuteCommand(String[] props) {
        if (props.length == 0) {
            throw new IllegalArgumentException("Error: no params entered");
        }

        ExecutorService execService = Executors.newFixedThreadPool(POOL_SIZE);

        CommandStore commandStore = new CommandStore();
        CommandExecutor commandExecutor = new CommandExecutor(
                new CreateUserCommand(commandStore),
                new GetUserListCommand(commandStore),
                new CreateTaskCommand(commandStore),
                new GetTasksListCommand(commandStore),
                new GetTaskByUsernameCommand(commandStore),
                new CompleteTaskCommand(commandStore),
                new DeleteUserCommand(commandStore),
                new CreateUserAndTaskCommand(commandStore)
        );

        try {
            execService.submit(() -> {
                commandExecutor.createUserAndTask(props);
                log.info("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
            }).get();

            execService.submit(() -> {
                commandExecutor.getUsers(props);
                log.info("22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
            }).get();

            execService.submit(() -> {
                commandExecutor.getTasks(props);
                log.info("3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            execService.shutdown();

            if(execService.isTerminated()) {
                log.info("Finished");
            }
        }
    }
}
