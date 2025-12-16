package com.college.eventclub;

import java.time.LocalDateTime;

/**
 * Club model class
 */
public class Club {
    private int clubId;
    private String clubName;
    private String description;
    private int createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Club() {}

    public Club(String clubName, String description, int createdBy) {
        this.clubName = clubName;
        this.description = description;
        this.createdBy = createdBy;
    }

    public Club(int clubId, String clubName, String description) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.description = description;
    }

    // Getters and Setters
    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return clubName;
    }
}
