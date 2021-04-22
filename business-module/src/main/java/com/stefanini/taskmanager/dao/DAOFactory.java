package com.stefanini.taskmanager.dao;

public interface DAOFactory {
    TaskDAO getTaskDAO();
    UserDAO getUserDAO();
}
