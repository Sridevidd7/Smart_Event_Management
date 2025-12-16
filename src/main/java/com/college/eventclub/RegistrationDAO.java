package com.college.eventclub;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * RegistrationDAO - Data Access Object for Registration table
 */
public class RegistrationDAO {

    private static volatile String registrationIdColumn = null;

    public static String getRegistrationIdColumn() {
        if (registrationIdColumn != null) return registrationIdColumn;
        synchronized (RegistrationDAO.class) {
            if (registrationIdColumn != null) return registrationIdColumn;
            try (Connection conn = DBConnection.getConnection()) {
                DatabaseMetaData md = conn.getMetaData();
                try (ResultSet rs = md.getColumns(null, null, "registrations", "registration_id")) {
                    if (rs.next()) {
                        registrationIdColumn = "registration_id";
                        return registrationIdColumn;
                    }
                }
                try (ResultSet rs2 = md.getColumns(null, null, "registrations", "reg_id")) {
                    if (rs2.next()) {
                        registrationIdColumn = "reg_id";
                        return registrationIdColumn;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error detecting registration id column: " + e.getMessage());
            }
            registrationIdColumn = "registration_id";
            return registrationIdColumn;
        }
    }

    /**
     * Add new registration
     */
    public int addRegistration(Registration registration) {
        String sql = "INSERT INTO registrations (event_id, user_id, qr_token, status) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, registration.getEventId());
            pstmt.setInt(2, registration.getUserId());
            pstmt.setString(3, registration.getQrToken());
            pstmt.setString(4, registration.getStatus());

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding registration: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Get registration by ID
     */
    public Registration getRegistrationById(int registrationId) {
        String col = getRegistrationIdColumn();
        String sql = "SELECT * FROM registrations WHERE " + col + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildRegistrationFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting registration: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get registration by QR token
     */
    public Registration getRegistrationByQRToken(String qrToken) {
        String sql = "SELECT * FROM registrations WHERE qr_token = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, qrToken);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildRegistrationFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting registration by token: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all registrations for an event
     */
    public List<Registration> getRegistrationsByEventId(int eventId) {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE event_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    registrations.add(buildRegistrationFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting registrations by event: " + e.getMessage());
        }
        return registrations;
    }

    /**
     * Get all registrations for a user
     */
    public List<Registration> getRegistrationsByUserId(int userId) {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT * FROM registrations WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    registrations.add(buildRegistrationFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting registrations by user: " + e.getMessage());
        }
        // Sort in Java by resolved registration id (descending) to avoid DB column name issues
        registrations.sort((a, b) -> Integer.compare(b.getRegistrationId(), a.getRegistrationId()));
        return registrations;
    }

    /**
     * Check if user is already registered for event
     */
    public boolean isUserRegisteredForEvent(int eventId, int userId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE event_id = ? AND user_id = ? " +
                     "AND status != 'CANCELLED'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);
            pstmt.setInt(2, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking registration: " + e.getMessage());
        }
        return false;
    }

    /**
     * Update registration status
     */
    public boolean updateRegistrationStatus(int registrationId, String status) {
        String col = getRegistrationIdColumn();
        String sql = "UPDATE registrations SET status = ? WHERE " + col + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, registrationId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating registration: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get registration count for event
     */
    public int getRegistrationCount(int eventId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE event_id = ? AND status != 'CANCELLED'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error counting registrations: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Helper method to build Registration object from ResultSet
     */
    private Registration buildRegistrationFromResultSet(ResultSet rs) throws SQLException {
        Registration registration = new Registration();
        // registration_id column may be named differently in some DBs (reg_id). Try both.
        try {
            registration.setRegistrationId(rs.getInt("registration_id"));
        } catch (SQLException e) {
            try {
                registration.setRegistrationId(rs.getInt("reg_id"));
            } catch (SQLException ex) {
                registration.setRegistrationId(0);
            }
        }

        registration.setEventId(rs.getInt("event_id"));
        registration.setUserId(rs.getInt("user_id"));
        registration.setQrToken(rs.getString("qr_token"));

        // registration_date may be null or named differently; try common name first
        try {
            Timestamp ts = rs.getTimestamp("registration_date");
            if (ts != null) {
                registration.setRegistrationDate(ts.toLocalDateTime());
            }
        } catch (SQLException ignore) {
            // ignore and leave registrationDate null
        }

        registration.setStatus(rs.getString("status"));
        return registration;
    }
}
