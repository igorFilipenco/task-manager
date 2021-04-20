package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.User;
import org.hibernate.Session;

import java.util.List;

public interface UserDAO {
    void createUser(User user);

    List<User> getUsers();

    User getUserByUserName(Session session, String userName);

    void deleteAllUsers();
}
