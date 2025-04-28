package io.vincent.learning.multi;

import java.sql.*;

/**
 * User Dao for test.
 */
public class UserDao {

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255), age INT)";
        try (Statement stmt = SimpleDBUtil.getConnection().createStatement()) {
            stmt.execute(sql);
        }
    }

    public void insertUser(String name, int age) throws SQLException {
        String sql = "INSERT INTO users(name, age) VALUES (?, ?)";
        try (PreparedStatement stmt = SimpleDBUtil.getConnection().prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.executeUpdate();

        }
    }

    public String getUserById(int id) throws SQLException {
        String sql = "SELECT name FROM users WHERE id = ?";
        try (PreparedStatement stmt = SimpleDBUtil.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        }
        return null;
    }

    public void updateUser(int id, String newName) throws SQLException {
        String sql = "UPDATE users SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = SimpleDBUtil.getConnection().prepareStatement(sql)) {
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = SimpleDBUtil.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}