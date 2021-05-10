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
import org.hibernate.Transaction;

import java.util.List;
import java.util.Objects;


public class UserServiceImpl implements UserService {
    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private final TaskService taskService ;
    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    public UserServiceImpl(TaskDAO taskDAO, UserDAO userDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
        this.taskService = new TaskServiceImpl(taskDAO, userDAO);
    }

    protected Session getCurrentSession() {
        return HibernateUtil.getSession();
    }
    @Override
    public User prepareUser(String[] args) {
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
        User user = prepareUser(args);
        Transaction transaction = null;

        try (Session session = getCurrentSession()) {
            User existingUser = userDAO.getUserByUserName(user.getUserName(), session);

            if (Objects.nonNull(existingUser)) {
                log.info("Error: user with username " + existingUser.getUserName() + " already exists");
                return;
            }

            transaction = session.beginTransaction();
            User newUser = userDAO.create(user, session);

            log.info("User create: created user " + newUser);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                e.printStackTrace();
            }

            log.error("User create error: " + e.getMessage());
        }
    }

    @Loggable
    //@Notifyable
    @Override
    public void createUserAndAssignTask(String[] args) {
        User user = prepareUser(args);
        Task task = taskService.prepareTask(args);
        Transaction transaction = null;

        try (Session session = getCurrentSession()) {
            transaction = session.beginTransaction();

            User createdUser = userDAO.create(user, session);
            Task createdTask = taskDAO.create(task, session);
            taskDAO.assignTask(createdUser, createdTask, session);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                log.error("User create and assign task error: " + e.getMessage());
            }
        }
    }

    @Loggable
    @Override
    public void getList() {
        try (Session session = getCurrentSession()) {
            List<User> userList = userDAO.getList(session);

            if (userList.size() == 0) {
                log.info("Get users: No users were created");
            } else {
                userList.forEach(user -> log.info("Get users: " + user));
            }
        } catch (Exception e) {
            log.error("Get users error: " + e.getMessage());
        }
    }

    @Loggable
    @Override
    public void delete(String[] args) {
        Transaction transaction = null;

        try (Session session = getCurrentSession()) {
            String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
            User userToDelete = userDAO.getUserByUserName(userName, session);

            if (Objects.isNull(userToDelete)) {
                log.error("User delete: user with username " + userName + " does not exist");

                return;
            }

            session.beginTransaction();

            User deletedUser = userDAO.delete(userToDelete.getId(), session);

            log.info("User delete: user " + deletedUser + " deleted");
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                log.error("User delete error: " + e.getMessage());
            }
        }
    }

    @Loggable
    public void deleteAll() {
        try (Session session = getCurrentSession()) {
            session.beginTransaction();

            userDAO.deleteAll(session);

            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Delete users: " + e.getMessage());
        }
    }
}
