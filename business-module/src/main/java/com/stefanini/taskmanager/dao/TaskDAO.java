package com.stefanini.taskmanager.dao;

import com.stefanini.taskmanager.entity.Task;

import java.util.List;


public interface TaskDAO {
    /**
     * Method adds new task and assigns it on user
     *
     * @param task
     * @param userName
     * @author igor
     */
    void createTask(Task task, String userName);

    /**
     * Method searches tasks which were assigned to concrete user
     *
     * @param userName
     * @return taskList can be empty, if user does not exist, or does not have tasks
     * @author igor
     */
    List<Task> getTasksByUsername(String userName);

    /**
     * Returns a list off all tasks
     *
     * @return taskList
     * @author igor
     */
    List<Task> getTasks();

    /**
     * Method removes link between user and task
     *
     * @param userName
     * @param taskTitle
     * @author igor
     */
    void completeTask(String userName, String taskTitle);

    /**
     * Removes all tasks from table and all records from relation table between user and task.
     * This is a service method usually used for preparing tests
     *
     * @author igor
     */
    void deleteAllTasks();
}
