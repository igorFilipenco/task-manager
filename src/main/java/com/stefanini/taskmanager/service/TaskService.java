package com.stefanini.taskmanager.service;

public interface TaskService {
    void createTask(String[] args);

    void getTasksByUsername(String[] args);

    void getTasks(String[] args);

    void completeTask(String[] args);
}
