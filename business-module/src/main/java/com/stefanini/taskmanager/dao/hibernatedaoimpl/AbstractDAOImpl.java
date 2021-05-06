package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.annotation.Loggable;
import com.stefanini.taskmanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public abstract class AbstractDAOImpl<T extends Serializable> {
    private static final Logger LOGGER = Logger.getLogger(AbstractDAOImpl.class);
    private Class<T> entityClass;
    private String className;

    public void setPersistentClass(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.className = entityClass == null ? "NoName" : entityClass.getName();
    }

    public Class<T> getPersistentClass() {
        return entityClass;
    }

    @Loggable
    public T create(T entity) {
        Session session = getCurrentSession();
        session.beginTransaction();
        Long newEntityId = (Long) session.save(entity);
        session.getTransaction().commit();

        return getOneById(newEntityId);
    }

    @Loggable
    public T getOneById(Long entityId) {
        if (entityId < 1) {
            throw new NullPointerException();
        }

        LOGGER.info(className + " search: Search for " + className + " entity with id " + entityId);

        T entity;

        entity = (T) getCurrentSession().get(entityClass, entityId);

        if (Objects.nonNull(entity)) {
            LOGGER.info(className + " search: found " + entity);
        } else {
            LOGGER.info(className + " search: user with id " + entityId + " not found");
        }

        return entity;
    }

    @Loggable
    public List<T> getList() {
        Session session = HibernateUtil.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> root = criteria.from(entityClass);
        criteria.select(root);

        List<T> entityList = session
                .createQuery(criteria)
                .getResultList();

        return entityList;
    }

    @Loggable
    public T delete(Long entityId) {
        T entityToDelete = getOneById(entityId);

        if (Objects.nonNull(entityToDelete)) {
            LOGGER.info(className + " delete: deleting " + className + " with id= " + entityId);

            Session session = getCurrentSession();
            session.beginTransaction();
            session.delete(entityToDelete);
            session.getTransaction().commit();

            LOGGER.info(className + " delete:" + className + " with id=" + entityId + " was deleted");
        }

        return entityToDelete;
    }

    @Loggable
    public void deleteAll() {
        LOGGER.info(className + " delete: deleting all " + className + " records");

        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM" + entityClass);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    protected Session getCurrentSession() {
        return HibernateUtil.getSession();
    }
}
