package com.stefanini.taskmanager.utils;


import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static final Logger log = Logger.getLogger(HibernateUtil.class);
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {

            try {
                // Create the SessionFactory from hibernate.cfg.xml
                Configuration configuration = new Configuration().configure();

                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Task.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

                return configuration.buildSessionFactory(builder.build());
            } catch (Throwable e) {
                log.error("Hibernate - Session factory create: cannot build factory due to " + e.getMessage());
                throw new ExceptionInInitializerError(e);
            }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
