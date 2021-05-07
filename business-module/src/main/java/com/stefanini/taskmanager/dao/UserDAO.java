package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.User;
import org.hibernate.Session;


public interface UserDAO extends AbstractDAO<User> {
    /**
     * Searches user by its username
     *
     * @param userName optional parameter passed to application on start
     * @return user if user found returns User instance
     * @param session Session session - hibernate session
     * @author igor
     */
    User getUserByUserName(String userName, Session session);
}
