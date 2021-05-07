package com.stefanini.taskmanager.dao;

import org.hibernate.Session;

import java.util.List;


public interface AbstractDAO<T> {
    /**
     * Inserts new entity as record to table
     *
     * @param entity  <T> entity
     * @param session Session session - hibernate session
     * @return creted entity
     * @author igor
     */
    T create(T entity, Session session);

    /**
     * Fetches instance by id
     *
     * @param session Session session - hibernate session
     * @return T entity returns fetched entity
     * @author igor
     */
    T getOneById(Long id, Session session);

    /**
     * Returns a list off all records from entity table
     *
     * @return List<T> entityList
     * @author igor
     */
    List<T> getList(Session session);

    /**
     * Deletes instance by id
     *
     * @param session Session session - hibernate session
     * @return T entity returns deleted entity
     * @author igor
     */
    T delete(Long id, Session session);

    /**
     * Method deletes all records from table
     *
     * @author igor
     */
    void deleteAll(Session session);
}
