package com.stefanini.taskmanager.dao.jdbcdaoimpl;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.dao.factory.DAOFactory;


public class JDBCDAOFactory implements DAOFactory {
    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }

    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOImpl();
    }
}
