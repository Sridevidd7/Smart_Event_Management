package com.college.eventclub;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * AttendanceDAO - Data Access Object for Attendance table
 */
public class AttendanceDAO {

    private static volatile String attendanceRegistrationCol = null;
    private static volatile String attendanceCheckInCol = null;
    private static volatile String attendanceCheckOutCol = null;

    /**
     * Resolve the column name used in `attendance` that references registrations.
     * Supports `registration_id` or `reg_id` and caches the result.
     */
    public static String getAttendanceRegistrationColumn() {
        if (attendanceRegistrationCol != null) return attendanceRegistrationCol;
        synchronized (AttendanceDAO.class) {
            if (attendanceRegistrationCol != null) return attendanceRegistrationCol;
            try (java.sql.Connection conn = DBConnection.getConnection()) {
                DatabaseMetaData md = conn.getMetaData();
                try (ResultSet rs = md.getColumns(null, null, "attendance", "registration_id")) {
                    if (rs.next()) {
                        attendanceRegistrationCol = "registration_id";
                        return attendanceRegistrationCol;
                    }
                }
                try (ResultSet rs2 = md.getColumns(null, null, "attendance", "reg_id")) {
                    if (rs2.next()) {
                        attendanceRegistrationCol = "reg_id";
                        return attendanceRegistrationCol;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error detecting attendance registration column: " + e.getMessage());
            }
            // fallback to registration_id
            attendanceRegistrationCol = "registration_id";
            return attendanceRegistrationCol;
        }
    }

    public static String getAttendanceCheckInColumn() {
        if (attendanceCheckInCol != null) return attendanceCheckInCol;
        synchronized (AttendanceDAO.class) {
            if (attendanceCheckInCol != null) return attendanceCheckInCol;
            try (java.sql.Connection conn = DBConnection.getConnection()) {
                DatabaseMetaData md = conn.getMetaData();
                try (ResultSet rs = md.getColumns(null, null, "attendance", "check_in_time")) {
                    if (rs.next()) {
                        attendanceCheckInCol = "check_in_time";
                        return attendanceCheckInCol;
                    }
                }
                try (ResultSet rs2 = md.getColumns(null, null, "attendance", "checkin_time")) {
                    if (rs2.next()) {
                        attendanceCheckInCol = "checkin_time";
                        return attendanceCheckInCol;
                    }
                }
                try (ResultSet rs3 = md.getColumns(null, null, "attendance", "checkin")) {
                    if (rs3.next()) {
                        attendanceCheckInCol = "checkin";
                        return attendanceCheckInCol;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error detecting attendance check-in column: " + e.getMessage());
            }
            attendanceCheckInCol = "check_in_time";
            return attendanceCheckInCol;
        }
    }

    public static String getAttendanceCheckOutColumn() {
        if (attendanceCheckOutCol != null) return attendanceCheckOutCol;
        synchronized (AttendanceDAO.class) {
            if (attendanceCheckOutCol != null) return attendanceCheckOutCol;
            try (java.sql.Connection conn = DBConnection.getConnection()) {
                DatabaseMetaData md = conn.getMetaData();
                try (ResultSet rs = md.getColumns(null, null, "attendance", "check_out_time")) {
                    if (rs.next()) {
                        attendanceCheckOutCol = "check_out_time";
                        return attendanceCheckOutCol;
                    }
                }
                try (ResultSet rs2 = md.getColumns(null, null, "attendance", "checkout_time")) {
                    if (rs2.next()) {
                        attendanceCheckOutCol = "checkout_time";
                        return attendanceCheckOutCol;
                    }
                }
                try (ResultSet rs3 = md.getColumns(null, null, "attendance", "checkout")) {
                    if (rs3.next()) {
                        attendanceCheckOutCol = "checkout";
                        return attendanceCheckOutCol;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Error detecting attendance check-out column: " + e.getMessage());
            }
            attendanceCheckOutCol = "check_out_time";
            return attendanceCheckOutCol;
        }
    }

    /**
     * Add attendance record
     */
    public boolean addAttendance(int registrationId) {
        String col = getAttendanceRegistrationColumn();
        String checkInCol = getAttendanceCheckInColumn();
        String sql = "INSERT INTO attendance (" + col + ", " + checkInCol + ") VALUES (?, CURRENT_TIMESTAMP)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding attendance: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get attendance by registration ID
     */
    public boolean isAttendanceMarked(int registrationId) {
        String col = getAttendanceRegistrationColumn();
        String sql = "SELECT COUNT(*) FROM attendance WHERE " + col + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking attendance: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get check-in time for registration
     */
    public LocalDateTime getCheckInTime(int registrationId) {
        String col = getAttendanceRegistrationColumn();
        String checkInCol = getAttendanceCheckInColumn();
        String sql = "SELECT " + checkInCol + " FROM attendance WHERE " + col + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Timestamp ts = rs.getTimestamp(checkInCol);
                    if (ts != null) {
                        return ts.toLocalDateTime();
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting check-in time: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get attendance count for event
     */
    public int getAttendanceCount(int eventId) {
        String regCol = RegistrationDAO.getRegistrationIdColumn();
        String attCol = getAttendanceRegistrationColumn();
        String sql = "SELECT COUNT(*) FROM attendance a " +
                     "INNER JOIN registrations r ON a." + attCol + " = r." + regCol + " " +
                     "WHERE r.event_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error counting attendance: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Mark check-out time
     */
    public boolean markCheckOut(int registrationId) {
        String col = getAttendanceRegistrationColumn();
        String checkOutCol = getAttendanceCheckOutColumn();
        String sql = "UPDATE attendance SET " + checkOutCol + " = CURRENT_TIMESTAMP WHERE " + col + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, registrationId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error marking check-out: " + e.getMessage());
        }
        return false;
    }
}
