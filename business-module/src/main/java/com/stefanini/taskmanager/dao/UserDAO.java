package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;


public interface UserDAO extends AbstractDAO<User>{
    /**
     * This method checks if user in database exists
     * If not - creates new one
     *
     * @param user user instance to be recorded to db
     * @author igor
     */
    User create(User user);

    /**
     * This method checks if user in database exists.
     * If not - creates new one
     * Creates task and assigns it to user
     *
     * @param user user instance to be recorded to db
     * @author igor
     */
    User createUserAndAssignTask(User user, Task task);

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
