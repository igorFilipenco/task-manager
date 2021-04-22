package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.entity.Task;

import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    @Override
    public void createTask(Task task, String userName) {

    }

    @Override
    public List<Task> getTasksByUsername(String userName) {
        return null;
    }

    @Override
    public List<Task> getTasks() {
        return null;
    }

    @Override
    public void completeTask(String userName, String taskTitle) {

    }

    @Override
    public void deleteAllTasks() {

    }
}
