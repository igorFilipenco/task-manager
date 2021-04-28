package com.stefanini.taskmanager.dao.jdbcdaoimpl;

import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.DBConnectionManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UserDAOImpl implements UserDAO {
    private static final Logger log = Logger.getLogger(UserDAOImpl.class);

    @Override
    public User create(User user) {
        String userName = user.getUserName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        User duplicateUser = getUserByUserName(userName);
        long newUserId = 0;
        Connection connection = null;
        PreparedStatement statement;

        try {
            if (Objects.isNull(duplicateUser)) {
                log.info("User create: creating new user with username " + userName);

                String query = "INSERT INTO `user`(userName, firstName, lastName) VALUES(?,?,?)";
                connection = DBConnectionManager.getConnection();
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, userName);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.executeUpdate();
                ResultSet result = statement.getGeneratedKeys();

                if (result.next()) {
                    newUserId = result.getLong(1);
                }
            } else {
                log.info("Error: user with username " + user.getUserName() + " already exists");

                return duplicateUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (Objects.nonNull(connection)) {
                try {
                    log.error("User create: transaction rollback due to error");
                    connection.rollback();
                } catch (SQLException rollExc) {
                    rollExc.printStackTrace();
                }
            }

            log.error("User create: user was not create because of error - " + e.getMessage());
        }

        return getOneById(newUserId);
    }

    @Override
    public User createUserAndAssignTask(User user, Task task) {
        //TODO implement method
        return  null;
    }

    @Override
    public User getOneById(Long userId) {
        User user = null;
        Connection connection;
        PreparedStatement statement;
        ResultSet result;

        try {
            String query = "SELECT * FROM `user` WHERE id = ?";

            log.info("User search: Search for user with id " + userId);

            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            result = statement.executeQuery();

            while (result.next()) {
                user = new User(result.getLong("id"),
                        result.getString("userName"),
                        result.getString("firstName"),
                        result.getString("lastName")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        if (Objects.isNull(user)) {
            log.info("User search: user with id " + userId + " not found");
        } else {
            log.info("User search: found user " + user);
        }

        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = null;
        Connection connection;
        PreparedStatement statement;
        ResultSet result;

        try {
            String query = "SELECT * FROM `user` WHERE userName = ?";
            log.info("User search: Search for user with userName " + userName);
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            result = statement.executeQuery();

            while (result.next()) {
                user = new User(result.getLong("id"),
                        result.getString("userName"),
                        result.getString("firstName"),
                        result.getString("lastName")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        if (Objects.isNull(user)) {
            log.info("User search: user with userName " + userName + " not found");
        } else {
            log.info("User search: found user " + user);
        }

        return user;
    }

    @Override
    public List<User> getList() {
        String query = "SELECT * FROM user";
        List<User> userList = new ArrayList<>();
        Connection connection;
        PreparedStatement statement;
        ResultSet result;

        try {
            log.info("Get users: Connecting to database");
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            result = statement.executeQuery();

            while (result.next()) {
                long id = result.getLong("id");
                String userName = result.getString("userName");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");

                userList.add(new User(id, userName, firstName, lastName));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return userList;
    }

    @Override
    public User delete(Long userId) {
        if (Objects.isNull(userId) || userId == 0) {
            return null;
        }

        User deletedUser = new User();
        String query = "DELETE FROM user WHERE id = (?)";
        Connection connection = null;
        PreparedStatement statement;

        deleteLinkToTaskByUserId(userId);

        try {
            log.info("User delete");

            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, userId);
            statement.executeUpdate();

            try {
                ResultSet generatedKeys = statement.getGeneratedKeys();

                while (generatedKeys.next()) {
                    deletedUser.setId(generatedKeys.getLong(1));
                    deletedUser.setUserName(generatedKeys.getString(2));
                    deletedUser.setFirstName(generatedKeys.getString(3));
                    deletedUser.setLastName(generatedKeys.getString(4));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            log.info("User delete: user with id=" + userId + " was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            if (Objects.nonNull(connection)) {
                try {
                    log.error("User delete: transaction rollback due to error");
                    connection.rollback();
                } catch (SQLException rollExc) {
                    rollExc.printStackTrace();
                }
            }

            log.error("User delete: user was not deleted because of error - " + e.getMessage());
        }

        return deletedUser;
    }

    public void deleteLinkToTaskByUserId(Long userId) {
        String linkQuery = "DELETE FROM user_task where user_id=" + userId;
        Connection connection = null;
        Statement statement;

        try {
            log.info("User task links delete");

            connection = DBConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(linkQuery);

            log.info("User task links delete: all tasks referred to user with id=" + userId + " deleted");
        } catch (SQLException e) {
            if (Objects.nonNull(connection)) {
                try {
                    log.info("User task links delete: transaction rollback due to " + e.getMessage());
                    connection.rollback();
                } catch (SQLException rollExc) {
                    rollExc.printStackTrace();
                }
            }
        }
    }

    @Override
    public void deleteAllUsers() {
        String query = "DELETE FROM user";
        Connection connection;
        Statement statement;

        try {
            log.info("User delete all users");
            connection = DBConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException e) {
            log.error("User error on delete all users due to - " + e.getMessage());
        }
    }
}
