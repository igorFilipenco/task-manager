package com.stefanini.taskmanager.service;

public interface BasicService {
    /**
     * Displays all entities fetched by DAO
     *
     * @author igor
     */
    void getList();

    /**
     * deletes entity using passed param
     *
     * @param parameterName String param - by this param entity will be found
     * @author igor
     */
    void delete(String[] parameterName);
}
