package com.stefanini.taskmanager.dao.factory;

import com.stefanini.taskmanager.dao.hibernatedaoimpl.HibernateDAOFactory;
import com.stefanini.taskmanager.dao.jdbcdaoimpl.JDBCDAOFactory;

public abstract class AbstractDAOFactory {
    public static DAOFactory createDAOFactory(String type) {
        switch (type) {
            case "jdbc":
                return new JDBCDAOFactory();
            case "hibernate":
                return new HibernateDAOFactory();
            default:
                return new JDBCDAOFactory();
        }
    }
}
