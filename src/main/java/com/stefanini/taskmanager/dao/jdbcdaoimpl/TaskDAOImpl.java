package com.stefanini.taskmanager.dao.jdbcdaoimpl;

import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.DBConnectionManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
        PreparedStatement taskStatement;
        PreparedStatement linkStatement;

        if (user == null) {
            log.info("Task create: user with username " + userName + " was not found. Task was not created");
            return;
        }

        try {
            String taskQuery = "INSERT INTO `task`(title, description) VALUES(?,?)";
            connection = DBConnectionManager.getConnection();
            taskStatement = connection.prepareStatement(taskQuery, Statement.RETURN_GENERATED_KEYS);
            taskStatement.setString(1, task.getTitle());
            taskStatement.setString(2, task.getDescription());
            int affectedRows = taskStatement.executeUpdate();

            if (affectedRows == 0) {
                log.error("Task create: error during task create, no rows affected");

                return;
            }

            int recordedTaskId = -1;
            ResultSet rs = taskStatement.getGeneratedKeys();

            if (rs.next()) {
                recordedTaskId = rs.getInt(1);
            }

            if (recordedTaskId < 0) {
                log.error("Task create: can't retrieve created task id");
            }

            String linkQuery = "INSERT INTO `user_task`(user_id, task_id) VALUES(?,?)";
            linkStatement = connection.prepareStatement(linkQuery);
            linkStatement.setInt(1, user.getId());
            linkStatement.setInt(2, recordedTaskId);
            linkStatement.executeUpdate();

            log.info("Task create: task was created and assigned on user " + userName);
        } catch (SQLException e) {
            if (Objects.nonNull(connection)) {
                try {
                    log.info("Task create: transaction rollback");
                    connection.rollback();
                } catch (SQLException rollExc) {
                    rollExc.printStackTrace();
                }
            }
        }
    }

    /**
     * Method searches tasks which were assigned to concrete user
     *
     * @param userName
     * @return taskList can be empty, if user does not exist, or does not have tasks
     * @author igor
     */
    @Override
    public List<Task> getTasksByUsername(String userName) {

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

    /**
     * Method removes link between user and task
     *
     * @param userName
     * @param taskTitle
     * @author igor
     */
    @Override
    public void completeTask(String userName, String taskTitle) {
        User user = userDAO.getUserByUserName(userName);

        if (user == null) {
            log.error("Task complete: user not found");
            return;
        }

        List<Task> userTasks = getTasksByUsername(userName);
        int taskId = -1;

        for (Task task : userTasks) {
            if (task.getTitle().equals(taskTitle)) taskId = task.getId();
        }

        if (taskId < 0) {
            log.error("Task complete: task with title " + taskTitle + " does not exist");
            return;
        }

        String deleteQuery = "DELETE FROM user_task where user_id=" + user.getId() + " AND task_id=" + taskId;
        Connection connection = null;
        Statement statement;

        try {
            connection = DBConnectionManager.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(deleteQuery);

            log.info("Task complete: task with title -" + taskTitle + " was removed from user " + userName);
        } catch (SQLException e) {
            if (Objects.nonNull(connection)) {
                try {
                    log.info("Task complete: transaction rollback due to " + e.getMessage());
                    connection.rollback();
                } catch (SQLException rollExc) {
                    rollExc.printStackTrace();
                }
            }
        }
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
