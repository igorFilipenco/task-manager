package com.stefanini.taskmanager.dao;

import java.util.List;

public interface AbstractDAO<T> {
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
     * Fetches instance by id
     *
     * @return T entity returns fetched entity
     * @author igor
     */
    T getOneById(Long id);
}
