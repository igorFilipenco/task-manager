package com.stefanini.taskmanager.dao.factory;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;

public interface DAOFactory {
    UserDAO getUserDAO();
    TaskDAO getTaskDAO();
}
