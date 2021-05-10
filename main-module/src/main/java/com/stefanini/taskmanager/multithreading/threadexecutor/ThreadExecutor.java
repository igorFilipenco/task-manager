package com.stefanini.taskmanager.multithreading.threadexecutor;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadExecutor {
    private static final Logger log = Logger.getLogger(ThreadExecutor.class);
    private int POOL_SIZE;
    private Collection<Runnable> threadTasks;
    private ExecutorService EXECUTOR;

    public ThreadExecutor(int poolSize, Collection<Runnable> threadTasks) {
        this.POOL_SIZE = poolSize;
        this.threadTasks = threadTasks;
    }

    public void executeTasksOneByOne() {
        EXECUTOR = Executors.newFixedThreadPool(POOL_SIZE);

        try {
            for (Runnable task : threadTasks) {
                EXECUTOR.submit(task).get();
            }
        } catch (ExecutionException | InterruptedException ex) {
            log.error("Commands error: " + ex.getMessage());
        } finally {
            EXECUTOR.shutdown();

            log.error("Finished");
        }
    }
}
