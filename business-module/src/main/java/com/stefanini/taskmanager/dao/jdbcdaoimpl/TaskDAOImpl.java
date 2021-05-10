package com.stefanini.taskmanager.dao.jdbcdaoimpl;

import com.mysql.cj.Session;
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

    public Task createAndAssignTask(Task task, String userName) {
        User user = userDAO.getUserByUserName(userName, null);
        Task newTask = null;
        Connection connection = null;
        PreparedStatement taskStatement;
        PreparedStatement linkStatement;

        if (Objects.isNull(user)) {
            log.info("Task create: user with username " + userName + " was not found. Task was not created");

            return newTask;
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

                return newTask;
            }

            long recordedTaskId = -1;
            ResultSet rs = taskStatement.getGeneratedKeys();

            if (rs.next()) {
                recordedTaskId = rs.getInt(1);
            }

            if (recordedTaskId < 0) {
                log.error("Task create: can't retrieve created task id");
            } else {
                newTask = getOneById(recordedTaskId, null);
            }

            String linkQuery = "INSERT INTO `user_task`(user_id, task_id) VALUES(?,?)";
            linkStatement = connection.prepareStatement(linkQuery);
            linkStatement.setLong(1, user.getId());
            linkStatement.setLong(2, recordedTaskId);
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

        return newTask;
    }

    @Override
    public List<Task> getTasksByUsername(String userName) {
        User user = userDAO.getUserByUserName(userName, null);
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
                userTasks.add(new Task(result.getLong("id"), result.getString("title"), result.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userTasks;
    }

    @Override
    public Task getTaskByTitle(String title, org.hibernate.Session session) {
        return null;
    }


    @Override
    public Task create(Task entity, org.hibernate.Session session) {
        return null;
    }

    @Override
    public Task getOneById(Long taskId, org.hibernate.Session session) {
        Task task = null;
        Connection connection;
        PreparedStatement statement;
        ResultSet result;

        try {
            String query = "SELECT * FROM `task` WHERE id = ?";
            log.info("Task search: Search for task with id " + taskId);
            connection = DBConnectionManager.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, taskId);
            result = statement.executeQuery();

            if (result.next()) {
                task =
                        new Task(
                                result.getLong(1),
                                result.getString(2),
                                result.getString(3)
                        );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        if (Objects.isNull(task)) {
            log.info("Task search: task with id " + taskId + " not found");
        } else {
            log.info("Task search: found task " + task);
        }

        return task;
    }

    @Override
    public List<Task> getList(org.hibernate.Session session) {
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
                long id = result.getInt("id");
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
        User user = userDAO.getUserByUserName(userName, null);

        if (user == null) {
            log.error("Task complete: user not found");
            return;
        }

        List<Task> userTasks = getTasksByUsername(userName);
        long taskId = -1;

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

    @Override
    public void assignTask(User user, Task task, org.hibernate.Session session) {

    }

    @Override
    public void deleteAll(org.hibernate.Session session) {
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

    @Override
    public Task delete(Long id, org.hibernate.Session session) {
        return null;
    }

    public void assignTaskToUser(Task task, String userName, Session session) {
//TODO implement this method for jdbc
    }
}
