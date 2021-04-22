package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.dao.DAOFactory;
import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;

public class HibernateDAOFactory implements DAOFactory {
    @Override
    public TaskDAO getTaskDAO() {
        return new TaskDAOImpl();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
