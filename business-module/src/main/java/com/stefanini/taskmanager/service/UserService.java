package com.stefanini.taskmanager.service;

public interface UserService extends BasicService{
    /**
     * Receives parameters which were passed to app. Creates User instance
     * Mapes fields with extracted parameters and calls user DAO
     *
     * @param args arguments which are passed to application on start
     * @author igor
     */
    void createUser(String[] args);
}
