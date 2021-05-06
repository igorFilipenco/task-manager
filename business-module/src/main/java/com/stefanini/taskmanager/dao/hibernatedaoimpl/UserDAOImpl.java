package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.annotation.Loggable;
import com.stefanini.taskmanager.annotation.Notifyable;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {
    private static final Logger log = Logger.getLogger(UserDAOImpl.class);

    {
        super.setPersistentClass(User.class);
    }

    //@Notifyable
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
    public User getUserByUserName(String userName) {
        if (Objects.isNull(userName)) {
            throw new NullPointerException();
        }

        log.info("User search: Search for user with username " + userName);

        User user = null;
        Session session = HibernateUtil.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        List<User> users = session
                .createQuery(criteria)
                .getResultList();

        user = users
                .stream()
                .filter(usr->usr.getUserName().equals(userName))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(user)) {
            log.info("User search: found user " + user);
        } else {
            log.info("User search: user with username " + userName + " not found");
        }

        session.close();

        return user;
    }
}
