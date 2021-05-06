package com.stefanini.taskmanager.dao;

import com.stefanini.taskmanager.entity.Task;

import java.util.List;


public interface TaskDAO extends AbstractDAO<Task>{
    /**
     * Method adds new task and assigns it on user
     *
     * @param task task entity provides field for inserting task record
     * @param userName is necessary to create link between inserted task and existing user
     * @author igor
     */
    Task createAndAssignTask(Task task, String userName);

    /**
     * Method searches tasks which were assigned to concrete user
     *
     * @param userName necessary parameter passed to application on start to find task using user_task table
     * @return taskList can be empty, if user does not exist, or does not have tasks
     * @author igor
     */
    List<Task> getTasksByUsername(String userName);

    /**
     * Method searches task by title
     *
     * @param title necessary parameter passed to application on start to find task using title
     * @return Task task can be empty, if task does not exist
     * @author igor
     */
    Task getTaskByTitle(String title);

    /**
     * Method removes link between user and task
     *
     * @param userName necessary parameter passed to application on start.
     *                 According to username, user_id will be found and user_task will be updated
     * @param taskTitle necessary parameter passed to application on start.
     *                  According to task title, task_id will be found and user_task will be updated
     * @author igor
     */
    void completeTask(String userName, String taskTitle);
}
