package com.stefanini.taskmanager.service;

public interface UserService {
    /**
     * Receives parameters which were passed to app. Creates User instance
     * Mapes fields with extracted parameters and calls user DAO
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void createUser(String[] args);

    /**
     * Calls user DAO to get all created users and displays them
     * @author igor
     */
    void getUsers();
}
