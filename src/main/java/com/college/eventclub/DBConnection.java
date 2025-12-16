package com.college.eventclub;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Connection utility class
 * Handles MySQL connection creation and management
 */
public class DBConnection {

    private static String DB_URL = "jdbc:mysql://localhost:3306/event_club_db";
    private static String DB_USER = "root";
    private static String DB_PASSWORD = "Sridevi@2006";

    static {
        // Try to load database configuration from config.properties in project root
        java.util.Properties props = new java.util.Properties();
        java.io.File configFile = new java.io.File("config.properties");
        if (configFile.exists()) {
            try (java.io.FileInputStream fis = new java.io.FileInputStream(configFile)) {
                props.load(fis);
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");
                if (url != null && !url.isBlank()) DB_URL = url;
                if (user != null && !user.isBlank()) DB_USER = user;
                if (pass != null) DB_PASSWORD = pass; // allow empty password if set
                System.out.println("DBConnection: loaded settings from config.properties (user='" + DB_USER + "')");
            } catch (Exception e) {
                System.err.println("DBConnection: failed to read config.properties, using defaults. " + e.getMessage());
            }
        } else {
            // No config file; using defaults
            System.out.println("DBConnection: using built-in defaults (user='" + DB_USER + "')");
        }
    }

    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database!");
            System.err.println("Make sure MySQL is running and database exists.");
            throw e;
        }
    }

    /**
     * Test the database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Database connection successful!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Close database resources
     * @param conn Connection to close
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
