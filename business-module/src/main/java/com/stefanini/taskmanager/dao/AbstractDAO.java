package com.stefanini.taskmanager.dao;

import java.util.List;


public interface AbstractDAO<T> {
    /**
     * Inserts new entity as record to table
     *
     * @param entity <T> entity
     * @return creted entity
     * @author igor
     */
    T create(T entity);

    /**
     * Fetches instance by id
     *
     * @return T entity returns fetched entity
     * @author igor
     */
    T getOneById(Long id);

    /**
     * Returns a list off all records from entity table
     *
     * @return List<T> entityList
     * @author igor
     */
    List<T> getList();

    /**
     * Deletes instance by id
     *
     * @return T entity returns deleted entity
     * @author igor
     */
    T delete(Long id);

    /**
     * Method deletes all records from table
     * @author igor
     */
    void deleteAll();
}
