package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TaskDAOImpl implements TaskDAO {
    private static final Logger log = Logger.getLogger(TaskDAOImpl.class);
    private static final UserDAO userDAO = new UserDAOImpl();

    @Override
    public Task create(Task task, String userName) {
        if (Objects.isNull(userName) || Objects.isNull(task)) {
            log.error("Task assign: wrong parameters to assign task. Task or username is null");
        }

        User user = userDAO.getUserByUserName(userName);
        Long newTaskId = 0L;

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSession();) {
            transaction = session.beginTransaction();

            log.info("Task create: creating task");

            newTaskId = (Long) session.save(task);

            log.info("Task create: assigning task to user " + userName);

            task.setId(newTaskId);
            user.addTask(task);

            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }

        return getOneById(newTaskId);
    }

    @Override
    public Task getOneById(Long taskId) {
        if (Objects.isNull(taskId) || taskId < 1) {
            throw new NullPointerException();
        }

        log.info("Task search: Search for task with id " + taskId);

        Task task = null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Task WHERE id=" + taskId);
        List<Task> tasks = query.getResultList();

        if (tasks.size() > 0) {
            task = tasks.get(0);

            log.info("Task search: found task " + task);
        } else {
            log.info("Task search: task with id " + taskId + " not found");
        }

        return task;
    }

    @Override
    public List<Task> getTasksByUsername(String userName) {
        return null;
    }

    @Override
    public Task getTaskByTitle(String title) {
        if (Objects.isNull(title)) {
            throw new NullPointerException();
        }

        log.info("Task search: Search for task with title " + title);

        Task task = null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("From Task  WHERE title=" + title);
        List<Task> tasks = query.getResultList();

        if (tasks.size() > 0) {
            task = tasks.get(0);

            log.info("Task search: found task " + task);
        } else {
            log.info("Task search: task with title " + title + " not found");
        }

        return task;
    }

    @Override
    public List<Task> getList() {
        log.info("Task get list: getting tasks list");

        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Task");
        List<Task> taskList = query.getResultList();

        return taskList;
    }

    @Override
    public void completeTask(String userName, String taskTitle) {
        Session session = HibernateUtil.getSession();

        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }

        session.beginTransaction();
        User user = userDAO.getUserByUserName(userName);
        Task task = getTaskByTitle(taskTitle);
        Set<Task> userTasks = user.getTasks();

        if (Objects.isNull(task)) {
            log.info("Task complete: task with title " + taskTitle + " does not exist");
            return;
        }

        if (userTasks.contains(task)) {
            user.completeTask(task);
            session.save(user);
            session.getTransaction().commit();

            System.out.println("Task complete: Task with title " + taskTitle + " has been removed");

        } else {
            System.out.println("Task complete: this user does not have tasks");
        }
    }

    @Override
    public void deleteAllTasks() {
        Session session = HibernateUtil.getSession();

        log.info("Tasks delete: deleting all tasks");

        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }

        session.beginTransaction();
        Query query = session.createQuery("delete from Task ");
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public Task delete(Long taskId) {
        Task taskToDelete = getOneById(taskId);

        if (Objects.nonNull(taskToDelete)) {
            log.info("Task delete: deleting task with id= " + taskId);

            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Task WHERE id=" + taskId);
            query.executeUpdate();
            session.getTransaction().commit();

            log.info("Task delete: task with id=" + taskId + " was deleted");
        }

        return taskToDelete;
    }
}
