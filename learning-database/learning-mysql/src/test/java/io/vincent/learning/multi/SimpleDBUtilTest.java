package io.vincent.learning.multi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SimpleDBUtilTest {

    private UserDao userDao;

    @BeforeAll
    void setup() throws SQLException {
        System.setProperty("env", "test");
        userDao = new UserDao();
        userDao.createTable();
        userDao.insertUser("Alice", 30);
        userDao.insertUser("Bob", 25);
    }

    @Test
    @Order(1)
    void testGetUser() throws SQLException {
        String name = userDao.getUserById(1);
        assertEquals("Alice", name);
    }

    @Test
    @Order(2)
    void testUpdateUser() throws SQLException {
        userDao.updateUser(2, "Bobby");
        String name = userDao.getUserById(2);
        assertEquals("Bobby", name);
    }

    @Test
    @Order(3)
    void testDeleteUser() throws SQLException {
        userDao.insertUser("Charlie", 28);
        userDao.deleteUser(3);
        assertNull(userDao.getUserById(3));
    }
}