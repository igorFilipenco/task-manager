package com.stefanini.taskmanager.service;

import com.stefanini.taskmanager.annotation.Loggable;
import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.HibernateUtil;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.apache.log4j.Logger;
import org.hibernate.Session;

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

    protected Session getCurrentSession() {
        return HibernateUtil.getSession();
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

    @Loggable
    @Override
    public void createUser(String[] args) {
        Session session = getCurrentSession();

        User user = prepareUser(args);
        User existingUser = userDAO.getUserByUserName(user.getUserName());

        if (Objects.isNull(existingUser)) {
            log.info("User create: creating new user with username" + user.getUserName());

            User newUser = userDAO.create(user);

            log.info("User create: created user data " + newUser);
        } else {
            log.info("Error: user with username " + existingUser.getUserName() + " already exists");
        }
    }

    @Loggable
    @Override
    public void createUserAndAssignTask(String[] args) {
        User user = prepareUser(args);
        Task task = taskService.prepareTask(args);

        userDAO.createUserAndAssignTask(user, task);
    }

    @Loggable
    @Override
    public void getList() {
        List<User> userList = userDAO.getList();

        if (userList.size() == 0) {
            log.info("Get users: No users were created");
        } else {
            userList.forEach(user -> log.info("Get users: " + user));
        }
    }

    @Loggable
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
