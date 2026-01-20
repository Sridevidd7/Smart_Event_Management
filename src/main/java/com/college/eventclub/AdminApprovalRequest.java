package com.college.eventclub;

import java.time.LocalDateTime;

/**
 * AdminApprovalRequest model class
 */
public class AdminApprovalRequest {
    private int requestId;
    private String name;
    private String email;
    private String password;
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDateTime requestedAt;
    private Integer reviewedBy;
    private LocalDateTime reviewedAt;
    private String comments;

    public AdminApprovalRequest() {}

    public AdminApprovalRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = "PENDING";
    }

    // Getters and Setters
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Integer getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Integer reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
