package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private static final Logger log = Logger.getLogger(UserDAOImpl.class);

    @Override
    public void createUser(User user) {
        String userName = user.getUserName();
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User existingUser = getUserByUserName(session, userName);

        if (existingUser == null) {
            session.save(user);
            session.getTransaction().commit();
        } else {
            log.error("Error: user with username " + user.getUserName() + " already exists");
        }
    }

    @Override
    public User getUserByUserName(Session session, String userName) {
        Query query = session.createQuery("From User U WHERE U.userName = :userName");
        query.setParameter("userName", userName);
        List<User> users = query.getResultList();

        if (users.size() > 0) {
            return users.get(0);
        }

        return null;
    }

    @Override
    public List<User> getUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User");
        List<User> userList = query.getResultList();

        return userList;
    }

    @Override
    public void deleteAllUsers() {
        Session session = HibernateUtil.getSession();

        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }

        session.beginTransaction();
        session.createQuery("delete from User").executeUpdate();
        session.getTransaction().commit();
    }
}
