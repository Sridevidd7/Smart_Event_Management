package com.college.eventclub;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Event model class
 */
public class Event {
    private int eventId;
    private String eventTitle;
    private int clubId;
    private LocalDate eventDate;
    private String venue;
    private String description;
    private String status; // UPCOMING, ONGOING, COMPLETED, CANCELLED
    private int createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Event() {}

    public Event(String eventTitle, int clubId, LocalDate eventDate, 
                 String venue, String description, int createdBy) {
        this.eventTitle = eventTitle;
        this.clubId = clubId;
        this.eventDate = eventDate;
        this.venue = venue;
        this.description = description;
        this.createdBy = createdBy;
        this.status = "UPCOMING";
    }

    // Getters and Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return eventTitle + " (" + eventDate + ")";
    }
}
