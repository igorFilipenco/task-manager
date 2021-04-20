package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.Task;

import java.util.List;

public interface TaskDAO {
    void createTask(Task task, String userName);

    List<Task> getTasksByUsername(String[] args);

    List<Task> getTasks(String[] args);

    void completeTask(String userName, String taskTitle);

    void deleteAllTasks();
}
