package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.User;

import java.util.List;

public interface UserDAO {
    /**
     * This method checks if user in database exists
     * If not - creates new one
     *
     * @param user user instance to be recorded to db
     * @author igor
     */
    void createUser(User user);

    /**
     * Returns a list off all users
     *
     * @return userList
     * @author igor
     */
    List<User> getUsers();

    /**
     * Searches user by its username
     *
     * @param userName optional parameter passed to application on start
     * @return user if user found returns User instance
     * @author igor
     */
    User getUserByUserName(String userName);

    /**
     * Removes all users from table. This is a service method usually used for preparing tests
     *
     * @author igor
     */
    void deleteAllUsers();
}
