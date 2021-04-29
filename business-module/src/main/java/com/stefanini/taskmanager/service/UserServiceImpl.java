package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;


public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private final TaskService taskService;
    private final UserDAO userDAO;

    public UserServiceImpl(TaskDAO taskDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.taskService = new TaskServiceImpl(taskDAO);
    }

    public static User prepareUser(String[] args) {
        User user = new User();
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        String firstName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.FIRSTNAME_FLAG);
        String lastName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.LASTNAME_FLAG);
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }

    @Override
    public void createUser(String[] args) {
        User user = prepareUser(args);

        User newUser = userDAO.create(user);
        log.info("User create: created user data " + newUser);
    }

    @Override
    public void createUserAndAssignTask(String[] args) {
        createUser(args);
        taskService.createTask(args);
    }

    @Override
    public void getList() {
        List<User> userList = userDAO.getList();

        if (userList.size() == 0) {
            log.info("Get users: No users were created");
        } else {
            userList.forEach(user -> log.info("Get users: " + user));
        }
    }

    @Override
    public void delete(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        User userToDelete = userDAO.getUserByUserName(userName);

        if (Objects.isNull(userToDelete)) {
            log.error("User delete: user with username " + userName + " does not exist");

            return;
        }

        User deletedUser = userDAO.delete(userToDelete.getId());

        log.info("User delete: user " + deletedUser + " deleted");
    }
}
