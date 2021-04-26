package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.dao.factory.DAOFactory;


public class HibernateDAOFactory implements DAOFactory {
    @Override
    public UserDAO getUserDAO() {
        return null;
    }

    @Override
    public TaskDAO getTaskDAO() {
        return null;
    }
}
