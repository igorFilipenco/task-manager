package com.stefanini.taskmanager.dao;

import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.DBConnectionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImpl implements UserDAO {
    private static final Logger log = Logger.getLogger(UserDAOImpl.class);
    private static Connection connection = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @Override
    public void createUser(User user) {
        String userName = user.getUserName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        User duplicateUser = getUserByUserName(userName);

        try {
            System.out.println(duplicateUser);
            if(duplicateUser == null) {
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
            log.error(e.getMessage());
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                log.error("ERROR: on closing resources");
            }
        }
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = null;

        try {
            String query = "SELECT * FROM `user` WHERE userName = ?";
            log.info("Connecting to database");
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            resultSet = statement.executeQuery();

            while(resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
            }
            //System.out.println(result);
            log.info("INFO: found user with username" + userName);


        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                log.error("ERROR: on closing resources");
            }
        }

        log.info("INFO: user with username" + userName + "does not exist");

        return user;
    }

    @Override
    public List<User> getUsers() {
        String query = "SELECT * FROM user";
        List<User> userList = new ArrayList<>();

        try {
            log.info("Connecting to database");
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                String userName = resultSet.getString("userName");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");

                userList.add(new User(id, userName, firstName, lastName));
            }


        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                log.error("ERROR: on closing resources");
            }
        }

        return userList;
    }

    @Override
    public void deleteAllUsers() {
        String query = "DELETE FROM user";

        try {
            log.info("Connecting to database");
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeQuery();
        } catch (SQLException e) {
            log.error(e.getMessage());
        } finally {
            try {
                connection.close();
                statement.close();
                resultSet.close();
            } catch (SQLException e) {
                log.error("ERROR: on closing resources");
            }
        }
    }
}
