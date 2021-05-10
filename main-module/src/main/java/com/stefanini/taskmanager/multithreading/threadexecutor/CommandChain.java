package com.stefanini.taskmanager.multithreading.threadexecutor;

import java.util.concurrent.CountDownLatch;

public class CommandChain implements Runnable {
    private final CountDownLatch waitLatch;
    private final Runnable command;
    private CommandChain then;

    private CommandChain(final CountDownLatch waitLatch, final Runnable command) {
        this.waitLatch = waitLatch;
        this.command = command;
    }

    public static CommandChain start(final Runnable command) {
        return new CommandChain(null, command);
    }

    public CommandChain then(final Runnable command) {
        then = new CommandChain(new CountDownLatch(1), command);
        return then;
    }

    @Override
    public void run() {
        if (waitLatch != null) {
            try {
                waitLatch.await();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        command.run();
        if (then != null) {
            then.waitLatch.countDown();
        }
    }
}
