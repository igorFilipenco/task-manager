package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.entity.User;


public interface UserService extends BasicService {
    /**
     *
     * @param args arguments which are passed to application on start
     * @return User user instance which is ready to persist to db
     * @author igor
     */
    User prepareUser(String[] args);

    /**
     * Receives parameters which were passed to app. Creates User instance
     * Mapes fields with extracted parameters and calls user DAO
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void createUser(String[] args);

    /**
     * Method prepares persist data and creates user, task entities and links them
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void createUserAndAssignTask(String[] args);
}
