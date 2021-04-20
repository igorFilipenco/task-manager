package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.HibernateUtil;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    private final UserDAO userRepository = new UserDAOImpl();
    private final Session repositorySession = HibernateUtil.getSession();

    @Override
    public void createTask(Task task, String userName) {
        repositorySession.beginTransaction();
        User user = userRepository.getUserByUserName(repositorySession, userName);

        if (user == null) {
            System.out.println("Error: user with user name " + userName + " does not exist");
        } else {
            user.addTask(task);
            repositorySession.save(user);
            repositorySession.save(task);
            repositorySession.getTransaction().commit();

            System.out.println("Success: task with title " + task.getTitle() + " has been added to user " + user.getUserName());
        }
    }

    @Override
    public List<Task> getTasksByUsername(String[] args) {
        repositorySession.beginTransaction();
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        User user = userRepository.getUserByUserName(repositorySession, userName);
        List<Task> userTasks = new ArrayList<>();

        if (user == null) {
            System.out.println("Error: user with username " + userName + " does not exist");
        } else {
            userTasks = user.getTasks();
        }

        return userTasks;
    }

    @Override
    public List<Task> getTasks(String[] args) {
        repositorySession.beginTransaction();
        Query query = repositorySession.createQuery("FROM Task");
        List<Task> taskList = query.getResultList();

        return taskList;
    }

    @Override
    public void completeTask(String userName, String taskTitle) {
        if (repositorySession.getTransaction().isActive()) {
            repositorySession.getTransaction().commit();
        }

        repositorySession.beginTransaction();
        User user = userRepository.getUserByUserName(repositorySession, userName);
        List<Task> userTasks = user.getTasks();
        int taskIndex = -1;

        if (userTasks != null) {
            for (Task task : userTasks) {
                if (task.getTitle().equals(taskTitle)) {
                    taskIndex = userTasks.indexOf(task);
                }
            }

            if (taskIndex < 0) {
                System.out.println("Error: task with title " + taskTitle + " does not exist");
            } else {
                user.completeTask(taskIndex);
                repositorySession.save(user);
                repositorySession.getTransaction().commit();

                System.out.println("Task with title " + taskTitle + " has been removed");
            }
        } else {
            System.out.println("Error: this user does not have tasks");
        }
    }

    @Override
    public void deleteAllTasks() {
        if (repositorySession.getTransaction().isActive()) {
            repositorySession.getTransaction().commit();
        }

        repositorySession.beginTransaction();
        repositorySession.createQuery("delete from Task ").executeUpdate();
        repositorySession.getTransaction().commit();
    }
}
