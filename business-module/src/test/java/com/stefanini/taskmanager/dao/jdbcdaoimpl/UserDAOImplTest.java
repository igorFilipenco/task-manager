package com.stefanini.taskmanager.dao.jdbcdaoimpl;

import com.stefanini.taskmanager.dao.UserDAO;
import com.stefanini.taskmanager.entity.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;


public class UserDAOImplTest {
    private static final UserDAO userDAO = new UserDAOImpl();
    public static final User U1 = new User("U1", "First name1", "Last name1");
    public static final User U2 = new User("U2", "First name2", "Last name2");

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void create() {

    }

    @Test
    public void getUserByUserName() {
    }

    @Test
    public void getList() {
    }
}