package com.stefanini.taskmanager.service;


import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.apache.log4j.Logger;

import java.util.List;


public class TaskServiceImpl implements TaskService {
    private static final Logger log = Logger.getLogger(TaskServiceImpl.class);
    private final TaskDAO taskDAO;

    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public void createTask(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        String taskTitle = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_TITLE_FLAG);
        String taskDescription = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_DESCRIPTION_FLAG);
        Task task = new Task(taskTitle, taskDescription);

        taskDAO.createTask(task, userName);
    }

    @Override
    public void getTasksByUsername(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        List<Task> userTasks = taskDAO.getTasksByUsername(userName);

        if (userTasks.size() == 0) {
            log.info("Task search: No tasks were assigned to this user");
        } else {
            userTasks.forEach(task -> log.info("Task search: " + task));
        }
    }

    @Override
    public void getTasks() {
        List<Task> taskList = taskDAO.getTasks();

        if (taskList.size() == 0) {
            log.info("Get tasks: no tasks created");
        } else {
            taskList.forEach(task -> log.info("Get tasks: " + task));
        }
    }


    @Override
    public void completeTask(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        String taskTitle = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_TITLE_FLAG);
        taskDAO.completeTask(userName, taskTitle);
    }
}
