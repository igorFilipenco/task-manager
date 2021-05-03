package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.annotation.Loggable;
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


public class UserDAOImpl implements UserDAO {
    private static final Logger log = Logger.getLogger(UserDAOImpl.class);

    @Loggable
    @Override
    public User create(User user) {
        String userName = user.getUserName();
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        User existingUser = getUserByUserName(userName);
        Long newUserId = null;

        if (Objects.isNull(existingUser)) {
            log.info("User create: creating new user with username" + userName);
            newUserId = (Long) session.save(user);
            session.getTransaction().commit();
        } else {
            log.info("Error: user with username " + userName + " already exists");
        }

        return getOneById(newUserId);
    }

    @Loggable
    @Override
    public User createUserAndAssignTask(User user, Task task) {
        String userName = user.getUserName();
        Transaction transaction = null;
        User existingUser = getUserByUserName(userName);
        Long newUserId = null;
        Long newTaskId = 0L;

        if (Objects.isNull(existingUser)) {
            try (Session session = HibernateUtil.getSession()) {
                transaction = session.beginTransaction();

                log.info("User create: creating new user with username" + userName);
                newUserId = (Long) session.save(user);

                log.info("Task create: creating task");
                newTaskId = (Long) session.save(task);

                log.info("Task create: assigning task to user " + userName);
                task.setId(newTaskId);
                user.addTask(task);
                session.saveOrUpdate(user);

                session.getTransaction().commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }

                e.printStackTrace();
            }
        } else {
            log.info("Error: user with username " + userName + " already exists");
        }

        return getOneById(newUserId);
    }

    @Loggable
    @Override
    public List<User> getList() {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User");
        List<User> userList = query.getResultList();

        return userList;
    }

    @Loggable
    @Override
    public User getOneById(Long userId) {
        if (Objects.isNull(userId) || userId < 1) {
            throw new NullPointerException();
        }

        log.info("User search: Search for user with id " + userId);

        User user = null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM User WHERE id=" + userId);
        List<User> users = query.getResultList();

        if (users.size() > 0) {
            user = users.get(0);

            log.info("User search: found user " + user);
        } else {
            log.info("User search: user with id " + userId + " not found");
        }

        return user;
    }

    @Loggable
    @Override
    public User getUserByUserName(String userName) {
        if (Objects.isNull(userName)) {
            throw new NullPointerException();
        }

        log.info("User search: Search for user with username " + userName);

        User user = null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("From User U WHERE U.userName = :userName");
        query.setParameter("userName", userName);
        List<User> users = query.getResultList();

        if (users.size() > 0) {
            user = users.get(0);

            log.info("User search: found user " + user);
        } else {
            log.info("User search: user with username " + userName + " not found");
        }

        session.close();

        return user;
    }

    @Loggable
    @Override
    public void deleteAllUsers() {
        Session session = HibernateUtil.getSession();

        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }

        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM User");
        query.executeUpdate();
        session.getTransaction().commit();
    }

    @Loggable
    @Override
    public User delete(Long userId) {
        User userToDelete = getOneById(userId);

        if (Objects.nonNull(userToDelete)) {
            log.info("User delete: deleting user with id= " + userId);

            Session session = HibernateUtil.getSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id=" + userId);
            query.executeUpdate();
            session.getTransaction().commit();

            log.info("User delete: user with id=" + userId + " was deleted");
        }

        return userToDelete;
    }
}
