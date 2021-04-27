package com.stefanini.taskmanager.service;

public interface TaskService extends BasicService{
    /**
     * Creates new task and links with passed user
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void createTask(String[] args);

    /**
     * Gets task by passed username
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void getTasksByUsername(String[] args);

    /**
     * Removes link between passed user and task
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void completeTask(String[] args);
}
