package com.stefanini.taskmanager.dao.hibernatedaoimpl;

import com.stefanini.taskmanager.annotation.Loggable;
import com.stefanini.taskmanager.dao.TaskDAO;
import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;
import com.stefanini.taskmanager.utils.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class TaskDAOImpl extends AbstractDAOImpl<Task> implements TaskDAO {
    private static final Logger log = Logger.getLogger(TaskDAOImpl.class);
    private static final UserDAO userDAO = new UserDAOImpl();

    {
        super.setPersistentClass(Task.class);
    }

    @Override
    public List<Task> getTasksByUsername(String userName) {
        return null;
    }

    @Override
    public Task getTaskByTitle(String title, Session session) {
        if (Objects.isNull(title)) {
            throw new NullPointerException();
        }

        log.info("Task search: Search for task with title " + title);

        Task task = null;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
        Root<Task> root = criteria.from(Task.class);

        criteria.select(root);

        List<Task> tasks = session
                .createQuery(criteria)
                .getResultList();

        task = tasks
                .stream()
                .filter(usr->usr.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(task)) {
            log.info("Task search: found task " + task);
        } else {
            log.info("Task search: task with title " + title + " not found");
        }

        return task;
    }

    @Override
    public void completeTask(String userName, String taskTitle) {
        Session session = HibernateUtil.getSession();

        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }

        session.beginTransaction();
        User user = userDAO.getUserByUserName(userName, session);
        Task task = getTaskByTitle(taskTitle, session);
        Set<Task> userTasks = user.getTasks();

        if (Objects.isNull(task)) {
            log.info("Task complete: task with title " + taskTitle + " does not exist");
            return;
        }

        if (userTasks.contains(task)) {
            user.completeTask(task);
            session.save(user);
            session.getTransaction().commit();

            System.out.println("Task complete: Task with title " + taskTitle + " has been removed");
        } else {
            System.out.println("Task complete: this user does not have tasks");
        }
    }

    @Loggable
    @Override
    public void assignTask(User user, Task task, Session session) {
        if (Objects.isNull(user) || Objects.isNull(task)) {
            log.error("Task assign: wrong parameters to assign task. Task or User is null");
        }

        user.addTask(task);
        session.saveOrUpdate(user);

        log.info("Task create: assigning task to user " + user.getUserName());
    }
}
