package com.stefanini.taskmanager.dao.jdbcdaoimpl;

import com.stefanini.taskmanager.dao.UserDAO;
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
    public void createUser(User user) {
        String userName = user.getUserName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        User duplicateUser = getUserByUserName(userName);
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            if (duplicateUser == null) {
                log.info("User create: creating new user with username " + userName);

                String query = "INSERT INTO `user`(userName, firstName, lastName) VALUES(?,?,?)";
                connection = DBConnectionManager.getConnection();
                statement = connection.prepareStatement(query);
                statement.setString(1, userName);
                statement.setString(2, firstName);
                statement.setString(3, lastName);
                statement.executeUpdate();
            } else {
                log.info("Error: user with username " + user.getUserName() + " already exists");
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
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            String query = "SELECT * FROM `user` WHERE userName = ?";
            log.info("User search: Search for user with userName " + userName);
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            result = statement.executeQuery();

            while (result.next()) {
                user = new User(result.getInt("id"),
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
    public List<User> getUsers() {
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
                int id = result.getInt("id");
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
