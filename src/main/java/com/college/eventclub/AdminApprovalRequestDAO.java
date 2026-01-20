package com.college.eventclub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * AdminApprovalRequestDAO - Data Access Object for AdminApprovalRequest table
 */
public class AdminApprovalRequestDAO {

    /**
     * Get all pending admin approval requests
     */
    public List<AdminApprovalRequest> getPendingRequests() {
        List<AdminApprovalRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM admin_approval_requests WHERE status = 'PENDING' ORDER BY requested_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AdminApprovalRequest request = new AdminApprovalRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setName(rs.getString("name"));
                request.setEmail(rs.getString("email"));
                request.setPassword(rs.getString("password"));
                request.setStatus(rs.getString("status"));
                request.setRequestedAt(rs.getTimestamp("requested_at").toLocalDateTime());
                requests.add(request);
            }
        } catch (SQLException e) {
            System.err.println("Error getting pending requests: " + e.getMessage());
        }
        return requests;
    }

    /**
     * Get all admin approval requests
     */
    public List<AdminApprovalRequest> getAllRequests() {
        List<AdminApprovalRequest> requests = new ArrayList<>();
        String sql = "SELECT * FROM admin_approval_requests ORDER BY requested_at DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AdminApprovalRequest request = new AdminApprovalRequest();
                request.setRequestId(rs.getInt("request_id"));
                request.setName(rs.getString("name"));
                request.setEmail(rs.getString("email"));
                request.setPassword(rs.getString("password"));
                request.setStatus(rs.getString("status"));
                request.setRequestedAt(rs.getTimestamp("requested_at").toLocalDateTime());
                if (rs.getObject("reviewed_by") != null) {
                    request.setReviewedBy(rs.getInt("reviewed_by"));
                }
                if (rs.getTimestamp("reviewed_at") != null) {
                    request.setReviewedAt(rs.getTimestamp("reviewed_at").toLocalDateTime());
                }
                request.setComments(rs.getString("comments"));
                requests.add(request);
            }
        } catch (SQLException e) {
            System.err.println("Error getting requests: " + e.getMessage());
        }
        return requests;
    }

    /**
     * Get request by ID
     */
    public AdminApprovalRequest getRequestById(int requestId) {
        String sql = "SELECT * FROM admin_approval_requests WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, requestId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    AdminApprovalRequest request = new AdminApprovalRequest();
                    request.setRequestId(rs.getInt("request_id"));
                    request.setName(rs.getString("name"));
                    request.setEmail(rs.getString("email"));
                    request.setPassword(rs.getString("password"));
                    request.setStatus(rs.getString("status"));
                    request.setRequestedAt(rs.getTimestamp("requested_at").toLocalDateTime());
                    if (rs.getObject("reviewed_by") != null) {
                        request.setReviewedBy(rs.getInt("reviewed_by"));
                    }
                    if (rs.getTimestamp("reviewed_at") != null) {
                        request.setReviewedAt(rs.getTimestamp("reviewed_at").toLocalDateTime());
                    }
                    request.setComments(rs.getString("comments"));
                    return request;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting request: " + e.getMessage());
        }
        return null;
    }

    /**
     * Approve admin request - Creates a new ADMIN user
     */
    public boolean approveRequest(int requestId, int reviewedBy) {
        AdminApprovalRequest request = getRequestById(requestId);
        if (request == null) {
            return false;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Create new ADMIN user
                String userSql = "INSERT INTO users (email, password, name, role) VALUES (?, ?, ?, 'ADMIN')";
                try (PreparedStatement pstmt = conn.prepareStatement(userSql)) {
                    pstmt.setString(1, request.getEmail());
                    pstmt.setString(2, request.getPassword());
                    pstmt.setString(3, request.getName());
                    pstmt.executeUpdate();
                }

                // Update request status
                String updateSql = "UPDATE admin_approval_requests SET status = 'APPROVED', reviewed_by = ?, reviewed_at = NOW() WHERE request_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    pstmt.setInt(1, reviewedBy);
                    pstmt.setInt(2, requestId);
                    pstmt.executeUpdate();
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Error approving request: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error in transaction: " + e.getMessage());
            return false;
        }
    }

    /**
     * Reject admin request
     */
    public boolean rejectRequest(int requestId, int reviewedBy, String comments) {
        String sql = "UPDATE admin_approval_requests SET status = 'REJECTED', reviewed_by = ?, reviewed_at = NOW(), comments = ? WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewedBy);
            pstmt.setString(2, comments);
            pstmt.setInt(3, requestId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error rejecting request: " + e.getMessage());
        }
        return false;
    }

    /**
     * Delete request by ID
     */
    public boolean deleteRequest(int requestId) {
        String sql = "DELETE FROM admin_approval_requests WHERE request_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, requestId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting request: " + e.getMessage());
        }
        return false;
    }
}
