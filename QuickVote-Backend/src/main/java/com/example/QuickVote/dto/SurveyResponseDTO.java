package com.example.QuickVote.dto;

import java.time.LocalDateTime;

public class SurveyResponseDTO {
    private String id;
    private String title;
    private LocalDateTime endTime;
    private boolean attempted; // ✅ Added field

    public SurveyResponseDTO(String id, String title, LocalDateTime endTime, boolean attempted) {
        this.id = id;
        this.title = title;
        this.endTime = endTime;
        this.attempted = attempted;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAttempted() {
        return attempted;
    }

    public void setAttempted(boolean attempted) {
        this.attempted = attempted;
    }
}
