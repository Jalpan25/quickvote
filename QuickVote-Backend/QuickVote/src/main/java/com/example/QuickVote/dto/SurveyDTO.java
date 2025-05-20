package com.example.QuickVote.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SurveyDTO {
    private String adminEmail;  // Added adminEmail field
    private String emailRestriction;
    private LocalDateTime endTime;
    private String title;
    private List<QuestionDTO> questions;
    private boolean resultShow;     // New field
    private int participationNo;

    public boolean isResultShow() {
        return resultShow;
    }

    public void setResultShow(boolean resultShow) {
        this.resultShow = resultShow;
    }

    public int getParticipationNo() {
        return participationNo;
    }

    public void setParticipationNo(int participationNo) {
        this.participationNo = participationNo;
    }

    // Getters and Setters
    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getEmailRestriction() {
        return emailRestriction;
    }

    public void setEmailRestriction(String emailRestriction) {
        this.emailRestriction = emailRestriction;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
