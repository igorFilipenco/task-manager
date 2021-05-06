package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;


public class TaskServiceImpl implements TaskService {
    private static final Logger log = Logger.getLogger(TaskServiceImpl.class);
    private final TaskDAO taskDAO;

    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public Task prepareTask(String[] args) {
        Task task = new Task();
        String taskTitle = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_TITLE_FLAG);
        String taskDescription = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_DESCRIPTION_FLAG);
        task.setTitle(taskTitle);
        task.setDescription(taskDescription);

        return task;
    }

    @Override
    public void createTask(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        Task task = prepareTask(args);

        taskDAO.create(task);
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
    public Task getTaskByTitle(String[] args) {
        String taskTitle = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_TITLE_FLAG);
        Task task = taskDAO.getTaskByTitle(taskTitle);

        if (Objects.isNull(task)) {
            log.info("Task delete: task with title " + taskTitle + " does not exist");
            return null;
        }

        return task;
    }

    @Override
    public void getList() {
        List<Task> taskList = taskDAO.getList();

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

    @Override
    public void delete(String[] args) {
        Task task = getTaskByTitle(args);

        if (Objects.nonNull(task)) {
            Task deletedTask = taskDAO.delete(task.getId());
        }
    }
}
