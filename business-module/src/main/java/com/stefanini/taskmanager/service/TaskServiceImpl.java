package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.annotation.Loggable;
import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.HibernateUtil;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;


public class TaskServiceImpl implements TaskService {
    private static final Logger log = Logger.getLogger(TaskServiceImpl.class);
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public TaskServiceImpl(TaskDAO taskDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    protected Session getCurrentSession() {
        return HibernateUtil.getSession();
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

    @Loggable
    @Override
    public void createTask(String[] args) {
        try (Session session = getCurrentSession()) {
            Task task = prepareTask(args);
            Transaction transaction = session.beginTransaction();

            Task createdTask = taskDAO.create(task, session);

            log.info("Task create: created task " + createdTask);

            transaction.commit();
        } catch (Exception e) {
            log.error("Task create  error: " + e.getMessage());
        }
    }

    @Loggable
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

    @Loggable
    @Override
    public Task getTaskByTitle(String[] args) {
        String taskTitle = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_TITLE_FLAG);
        Task task = null;

        try (Session session = getCurrentSession()) {
            task = taskDAO.getTaskByTitle(taskTitle, session);

            if (Objects.isNull(task)) {
                log.info("Task delete: task with title " + taskTitle + " does not exist");
                return null;
            }
        } catch (Exception e) {

            log.error("Task search error: " + e.getMessage());

        }

        return task;
    }

    @Loggable
    @Override
    public void getList() {
        try (Session session = getCurrentSession()) {
            List<Task> taskList = taskDAO.getList(session);

            if (taskList.size() == 0) {
                log.info("Get tasks: no tasks created");
            } else {
                taskList.forEach(task -> log.info("Get tasks: " + task));
            }
        } catch (Exception e) {
            log.error("Get tasks error: " + e.getMessage());
        }
    }

    @Loggable
    @Override
    public void completeTask(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        String taskTitle = ParamsExtractor.getParamFromArg(args, ParamsExtractor.TASK_TITLE_FLAG);
        taskDAO.completeTask(userName, taskTitle);
    }

    @Loggable
    @Override
    public void assignTask(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        Transaction transaction = null;

        try (Session session = getCurrentSession()) {
            User user = userDAO.getUserByUserName(userName, session);
            Task task = getTaskByTitle(args);

            transaction = session.beginTransaction();

            taskDAO.assignTask(user, task, session);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                log.error("Task assign error: " + e.getMessage());
            }
        }
    }

    @Loggable
    @Override
    public void delete(String[] args) {
        Transaction transaction = null;

        try (Session session = getCurrentSession()) {
            Task task = getTaskByTitle(args);

            if (Objects.isNull(task)) {
                log.error("Task delete: task does not exits");
                return;
            }

            session.beginTransaction();

            Task deletedTask = taskDAO.delete(task.getId(), session);

            log.info("Task delete: task " + deletedTask + " deleted");
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                log.error("User delete error: " + e.getMessage());
            }
        }
    }
}
