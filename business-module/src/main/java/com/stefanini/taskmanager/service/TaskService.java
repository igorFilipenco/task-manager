package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.entity.Task;


public interface TaskService extends BasicService{
    /**
     *
     * @param args arguments which are passed to application on start
     * @return Task task instance which is ready to persist to db
     * @author igor
     */
    Task prepareTask(String[] args);

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
     * Gets task by passed task title
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    Task getTaskByTitle(String[] args);

    /**
     * Removes link between passed user and task
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void completeTask(String[] args);

    /**
     * Method creates link between user and task in link table
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void assignTask(String[] args);
}
