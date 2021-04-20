package com.stefanini.taskmanager.dao;

import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class TaskDAOImpl implements TaskDAO {
    private static final Logger log = Logger.getLogger(TaskDAOImpl.class);
    private final UserDAO userRepository = new UserDAOImpl();

    @Override
    public void createTask(Task task, String userName) {
//        repositorySession.beginTransaction();
//        User user = userRepository.getUserByUserName(repositorySession, userName);
//
//        if (user == null) {
//            log.error("Error: user with user name " + userName + " does not exist");
//        } else {
//            user.addTask(task);
//            repositorySession.save(user);
//            repositorySession.save(task);
//            repositorySession.getTransaction().commit();
//
//            log.info("Success: task with title " + task.getTitle() + " has been added to user " + user.getUserName());
//        }
    }

    @Override
    public List<Task> getTasksByUsername(String[] args) {
//        repositorySession.beginTransaction();
//        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
//        User user = userRepository.getUserByUserName(repositorySession, userName);
        List<Task> userTasks = new ArrayList<>();

//        if (user == null) {
//            log.error("Error: user with user name " + userName + " does not exist");
//        } else {
//            userTasks = user.getTasks();
//        }

        return userTasks;
    }

    @Override
    public List<Task> getTasks(String[] args) {
//        repositorySession.beginTransaction();
//        Query query = repositorySession.createQuery("FROM Task");
//        List<Task> taskList = query.getResultList();
        List<Task> taskList = new ArrayList<>();

        return taskList;
    }

    @Override
    public void completeTask(String userName, String taskTitle) {
//        if (repositorySession.getTransaction().isActive()) {
//            repositorySession.getTransaction().commit();
//        }
//
//        repositorySession.beginTransaction();
//        User user = userRepository.getUserByUserName(repositorySession, userName);
//        List<Task> userTasks = new ArrayList<>();
//        //List<Task> userTasks = user.getTasks();
//        int taskIndex = -1;
//
//        if (userTasks != null) {
//            for (Task task : userTasks) {
//                if (task.getTitle().equals(taskTitle)) {
//                    taskIndex = userTasks.indexOf(task);
//                }
//            }
//
//            if (taskIndex < 0) {
//                log.error("Error: task with title " + taskTitle + " does not exist");
//            } else {
//                user.completeTask(taskIndex);
//                repositorySession.save(user);
//                repositorySession.getTransaction().commit();
//
//                log.error("Task with title " + taskTitle + " has been removed");
//            }
//        } else {
//            log.error("Error: this user does not have tasks");
//        }
    }

    @Override
    public void deleteAllTasks() {
//        if (repositorySession.getTransaction().isActive()) {
//            repositorySession.getTransaction().commit();
//        }
//
//        repositorySession.beginTransaction();
//        //repositorySession.createQuery("delete from Task ").executeUpdate();
//        repositorySession.getTransaction().commit();
    }
}
