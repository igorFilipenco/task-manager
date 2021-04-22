package com.stefanini.taskmanager.dao;


import com.stefanini.taskmanager.entity.User;

import java.util.List;

public interface UserDAO {
    void createUser(User user);

    List<User> getUsers();

    User getUserByUserName(String userName);

    void deleteAllUsers();
}
