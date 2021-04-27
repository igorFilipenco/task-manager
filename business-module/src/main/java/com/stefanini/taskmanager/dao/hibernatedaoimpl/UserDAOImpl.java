package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.User;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User create(User user) {
        return new User();
    }

    @Override
    public List<User> getList() {
        return null;
    }

    @Override
    public User getOneById(Long id) {
        return null;
    }

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public void deleteAllUsers() {

    }

    @Override
    public void deleteLinkToTaskByUserId(Long userId) {

    }

    @Override
    public User delete(Long id) {
        return null;
    }
}
