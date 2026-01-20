package com.college.eventclub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO - Data Access Object for User table
 */
public class UserDAO {

    /**
     * Get user by email and password (Login)
     */
    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get user by ID
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get user by email
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Add new user
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (email, password, name, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getRole());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get all students
     */
    public List<User> getAllStudents() {
        List<User> students = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = 'STUDENT'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                students.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting students: " + e.getMessage());
        }
        return students;
    }

    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting users: " + e.getMessage());
        }
        return users;
    }

    /**
     * Update user
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET email = ?, name = ?, role = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getRole());
            pstmt.setInt(4, user.getUserId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
        return false;
    }

    /**
     * Delete user
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }

    /**
     * Count total admin users
     */
    public int countAdminUsers() {
        String sql = "SELECT COUNT(*) as count FROM users WHERE role = 'ADMIN'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Error counting admin users: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Register admin approval request (for pending admin registrations)
     */
    public boolean registerAdminRequest(String name, String email, String password) {
        String sql = "INSERT INTO admin_approval_requests (name, email, password, status) VALUES (?, ?, ?, 'PENDING')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error registering admin request: " + e.getMessage());
        }
        return false;
    }
}
