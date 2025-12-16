package com.college.eventclub;

import java.time.LocalDateTime;

/**
 * Registration model class
 */
public class Registration {
    private int registrationId;
    private int eventId;
    private int userId;
    private String qrToken;
    private LocalDateTime registrationDate;
    private String status; // REGISTERED, CHECKED_IN, CANCELLED

    public Registration() {}

    public Registration(int eventId, int userId, String qrToken) {
        this.eventId = eventId;
        this.userId = userId;
        this.qrToken = qrToken;
        this.status = "REGISTERED";
    }

    // Getters and Setters
    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationId=" + registrationId +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
