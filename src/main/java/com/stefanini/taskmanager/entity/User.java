package com.stefanini.taskmanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "user_name")
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")

    private String userName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "user_tasks",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "task_id") }
    )
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
