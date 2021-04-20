package com.stefanini.taskmanager.service;


import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.dao.UserDAOImpl;
import com.stefanini.taskmanager.utils.ParamsExtractor;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final UserDAO userRepository = new UserDAOImpl();

    @Override
    public void createUser(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        String firstName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.FIRSTNAME_FLAG);
        String lastName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.LASTNAME_FLAG);
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        userRepository.createUser(user);
    }

    @Override
    public void getUsers() {
        List<User> userList = userRepository.getUsers();

        if (userList.size() == 0) {
            System.out.println("No users were created");
        } else {
            userList.forEach(System.out::println);
        }
    }
}
