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
}
