package com.college.eventclub;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * EventDAO - Data Access Object for Event table
 */
public class EventDAO {

    /**
     * Add new event
     */
    public boolean addEvent(Event event) {
        String sql = "INSERT INTO events (club_id, title, description, event_date, venue) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, event.getClubId());
            pstmt.setString(2, event.getEventTitle());
            pstmt.setString(3, event.getDescription());
            pstmt.setDate(4, Date.valueOf(event.getEventDate()));
            pstmt.setString(5, event.getVenue());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get event by ID
     */
    public Event getEventById(int eventId) {
        String sql = "SELECT * FROM events WHERE event_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return buildEventFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting event: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all events
     */
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events ORDER BY event_date DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                events.add(buildEventFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting events: " + e.getMessage());
        }
        return events;
    }

    /**
     * Get events by club ID
     */
    public List<Event> getEventsByClubId(int clubId) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE club_id = ? ORDER BY event_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clubId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    events.add(buildEventFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting events by club: " + e.getMessage());
        }
        return events;
    }

    /**
     * Get upcoming events
     */
    public List<Event> getUpcomingEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE event_date >= CURDATE() ORDER BY event_date ASC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                events.add(buildEventFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting upcoming events: " + e.getMessage());
        }
        return events;
    }

    /**
     * Update event
     */
    public boolean updateEvent(Event event) {
        String sql = "UPDATE events SET title = ?, club_id = ?, event_date = ?, venue = ?, description = ? WHERE event_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, event.getEventTitle());
            pstmt.setInt(2, event.getClubId());
            pstmt.setDate(3, Date.valueOf(event.getEventDate()));
            pstmt.setString(4, event.getVenue());
            pstmt.setString(5, event.getDescription());
            pstmt.setInt(6, event.getEventId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error updating event: " + e.getMessage());
        }
        return false;
    }

    /**
     * Delete event
     */
    public boolean deleteEvent(int eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, eventId);

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get event count
     */
    public int getEventCount() {
        String sql = "SELECT COUNT(*) FROM events";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error counting events: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Helper method to build Event object from ResultSet
     */
    private Event buildEventFromResultSet(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setEventId(rs.getInt("event_id"));
        event.setEventTitle(rs.getString("title"));
        event.setClubId(rs.getInt("club_id"));
        event.setEventDate(rs.getDate("event_date").toLocalDate());
        event.setVenue(rs.getString("venue"));
        event.setDescription(rs.getString("description"));
        return event;
    }
}
