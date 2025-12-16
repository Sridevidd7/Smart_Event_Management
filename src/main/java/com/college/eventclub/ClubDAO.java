package com.college.eventclub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * ClubDAO - Data Access Object for Club table
 */
public class ClubDAO {

    /**
     * Add new club
     */
    public boolean addClub(Club club) {
        String sql = "INSERT INTO clubs (club_name, description) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getDescription());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding club: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get club by ID
     */
    public Club getClubById(int clubId) {
        String sql = "SELECT * FROM clubs WHERE club_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clubId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Club club = new Club();
                    club.setClubId(rs.getInt("club_id"));
                    club.setClubName(rs.getString("club_name"));
                    club.setDescription(rs.getString("description"));
                    return club;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting club: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all clubs
     */
    public List<Club> getAllClubs() {
        List<Club> clubs = new ArrayList<>();
        String sql = "SELECT * FROM clubs ORDER BY club_name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Club club = new Club();
                club.setClubId(rs.getInt("club_id"));
                club.setClubName(rs.getString("club_name"));
                club.setDescription(rs.getString("description"));
                clubs.add(club);
            }
        } catch (SQLException e) {
            System.err.println("Error getting clubs: " + e.getMessage());
        }
        return clubs;
    }

    /**
     * Update club
     */
    public boolean updateClub(Club club) {
        String sql = "UPDATE clubs SET club_name = ?, description = ? WHERE club_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, club.getClubName());
            pstmt.setString(2, club.getDescription());
            pstmt.setInt(3, club.getClubId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating club: " + e.getMessage());
        }
        return false;
    }

    /**
     * Delete club
     */
    public boolean deleteClub(int clubId) {
        String sql = "DELETE FROM clubs WHERE club_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clubId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting club: " + e.getMessage());
        }
        return false;
    }

    /**
     * Check if club name exists
     */
    public boolean clubNameExists(String clubName) {
        String sql = "SELECT COUNT(*) FROM clubs WHERE club_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, clubName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking club existence: " + e.getMessage());
        }
        return false;
    }
}
