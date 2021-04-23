package com.stefanini.taskmanager.service;

public interface TaskService {
    /**
     * Creates new task and links with passed user
     *
     * @param args
     * @author igor
     */
    void createTask(String[] args);

    /**
     * Gets task by passed username
     *
     * @param args
     * @author igor
     */
    void getTasksByUsername(String[] args);

    /**
     * Displays all tasks fetched by taskDAO
     *
     * @author igor
     */
    void getTasks();

    /**
     * Removes link between passed user and task
     *
     * @param args
     * @author igor
     */
    void completeTask(String[] args);
}