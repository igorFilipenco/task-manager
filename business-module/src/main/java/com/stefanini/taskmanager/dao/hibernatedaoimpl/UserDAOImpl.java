package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.annotation.Loggable;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;


public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {
    private static final Logger log = Logger.getLogger(UserDAOImpl.class);

    {
        super.setPersistentClass(User.class);
    }

    @Loggable
    @Override
    public User getUserByUserName(String userName, Session session) {
        if (Objects.isNull(userName)) {
            throw new NullPointerException();
        }

        log.info("User search: Search for user with username " + userName);

        User user;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        List<User> users = session
                .createQuery(criteria)
                .getResultList();

        user = users
                .stream()
                .filter(usr->usr.getUserName().equals(userName))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(user)) {
            log.info("User search: found user " + user);
        } else {
            log.info("User search: user with username " + userName + " not found");
        }

        session.close();

        return user;
    }
}
