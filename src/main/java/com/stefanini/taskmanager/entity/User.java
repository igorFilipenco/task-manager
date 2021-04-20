package com.stefanini.taskmanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class User implements Serializable {
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private List<Task> tasks;

    public User() {

    }

    public User(String userName, String firstName, String lastName, List<Task> tasks) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }

        tasks.add(task);
    }

    public void completeTask(int index) {
        tasks.remove(index);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
