package com.example.QuickVote.dto;

public class FilterDTO {
    private Long id;
    private String title;
    private String endTime;
    private boolean attempted;
    private boolean result_show;

    public boolean isResult_show() {
        return result_show;
    }

    public void setResult_show(boolean result_show) {
        this.result_show = result_show;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isAttempted() {
        return attempted;
    }

    public void setAttempted(boolean attempted) {
        this.attempted = attempted;
    }
}
