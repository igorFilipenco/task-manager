package com.stefanini.taskmanager.dao.jdbcdaoimpl;

import com.stefanini.taskmanager.dao.DAOFactory;
import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;

public class JDBCDAOFactoryImpl implements DAOFactory {
    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOImpl();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
