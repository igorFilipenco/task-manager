package com.stefanini.taskmanager.dao;

import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.DBConnectionManager;
import com.stefanini.taskmanager.utils.ParamsExtractor;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TaskDAOImpl implements TaskDAO {
    private static final Logger log = Logger.getLogger(TaskDAOImpl.class);
    private final UserDAO userDAO = new UserDAOImpl();

    /**
     * Method adds new task and assigns it on user
     *
     * @param task
     * @param userName
     * @author igor
     */
    @Override
    public void createTask(Task task, String userName) {
        User user = userDAO.getUserByUserName(userName);
        Connection connection = null;
        Statement taskStatement;
        PreparedStatement linkStatement;

        if (user == null) {
            log.info("Task create: user with username " + userName + " was not found. Task was not created");
            return;
        }

        try {
            String taskQuery = "INSERT INTO `task`(title, description) VALUES(" + "'" + task.getTitle() + "'" + "," + "'" + task.getDescription() + "'" + ")";
            connection = DBConnectionManager.getConnection();
            taskStatement = connection.createStatement();
            connection.setAutoCommit(false);
            int recordedTaskId = taskStatement.executeUpdate(taskQuery, Statement.RETURN_GENERATED_KEYS);
            String linkQuery = "INSERT INTO `user_task`(user_id, task_id) VALUES(?,?)";
            linkStatement = connection.prepareStatement(linkQuery);
            linkStatement.setInt(1, user.getId());
            linkStatement.setInt(2, recordedTaskId);
            linkStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);

            log.info("Task create: task was created and assigned on user " + userName);
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    log.info("Task create: transaction rollback");
                    connection.rollback();
                } catch (SQLException rollExc) {
                    rollExc.printStackTrace();
                }
            }

            log.error(e.getMessage());
        }
    }

    /**
     * Method searches tasks which were assigned to concrete user
     *
     * @param args
     * @return taskList can be empty, if user does not exist, or does not have tasks
     * @author igor
     */
    @Override
    public List<Task> getTasksByUsername(String[] args) {
        String userName = ParamsExtractor.getParamFromArg(args, ParamsExtractor.USERNAME_FLAG);
        User user = userDAO.getUserByUserName(userName);
        List<Task> userTasks = new ArrayList<>();
        Connection connection;
        Statement statement;
        ResultSet result;

        if (user == null) {
            log.info("Task get by username: user with username " + userName + " does not exist");

            return userTasks;
        }

        try {
            String query = "SELECT t.* FROM task AS t JOIN user_task AS ut ON t.id = ut.task_id WHERE  user_id = " + user.getId();
            connection = DBConnectionManager.getConnection();
            statement = connection.createStatement();
            result = statement.executeQuery(query);

            while (result.next()) {
                userTasks.add(new Task(result.getInt("id"), result.getString("title"), result.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userTasks;
    }

    /**
     * Returns a list off all tasks
     *
     * @return taskList
     * @author igor
     */
    @Override
    public List<Task> getTasks() {
        String query = "SELECT * FROM task";
        List<Task> taskList = new ArrayList<>();
        Connection connection;
        PreparedStatement statement;
        ResultSet result;

        try {
            log.info("Get tasks: Connecting to database");
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String title = result.getString("title");
                String description = result.getString("description");

                taskList.add(new Task(id, title, description));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return taskList;
    }

    @Override
    public void completeTask(String userName, String taskTitle) {
//        if (repositorySession.getTransaction().isActive()) {
//            repositorySession.getTransaction().commit();
//        }
//
//        repositorySession.beginTransaction();
//        User user = userRepository.getUserByUserName(repositorySession, userName);
//        List<Task> userTasks = new ArrayList<>();
//        //List<Task> userTasks = user.getTasks();
//        int taskIndex = -1;
//
//        if (userTasks != null) {
//            for (Task task : userTasks) {
//                if (task.getTitle().equals(taskTitle)) {
//                    taskIndex = userTasks.indexOf(task);
//                }
//            }
//
//            if (taskIndex < 0) {
//                log.error("Error: task with title " + taskTitle + " does not exist");
//            } else {
//                user.completeTask(taskIndex);
//                repositorySession.save(user);
//                repositorySession.getTransaction().commit();
//
//                log.error("Task with title " + taskTitle + " has been removed");
//            }
//        } else {
//            log.error("Error: this user does not have tasks");
//        }
    }

    /**
     * Removes all tasks from table and all records from relation table between user and task.
     * This is a service method usually used for preparing tests
     *
     * @author igor
     */
    @Override
    public void deleteAllTasks() {
        String query = "DELETE FROM task";
        String linkQuery = "DELETE FROM user_task";
        Connection connection;
        Statement taskStatement;
        Statement linkStatement;

        try {
            log.info("Task delete all tasks");
            connection = DBConnectionManager.getConnection();
            taskStatement = connection.createStatement();
            taskStatement.executeQuery(query);
            linkStatement = connection.createStatement();
            linkStatement.executeQuery(linkQuery);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

    }
}
