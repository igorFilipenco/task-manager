package com.stefanini.taskmanager.dao.factory;

import com.stefanini.taskmanager.dao.hibernatedaoimpl.HibernateDAOFactory;
import com.stefanini.taskmanager.dao.jdbcdaoimpl.JDBCDAOFactory;


public abstract class AbstractDAOFactory {
    public static DAOFactory createDAOFactory(FactoryType type) {
        switch (type) {
            case JDBC:
                return new JDBCDAOFactory();
            case HIBERNATE:
                return new HibernateDAOFactory();
            default:
                throw new IllegalArgumentException("ERROR : wrong DAO factory type passed to abstract DAO factory");
        }
    }
}
