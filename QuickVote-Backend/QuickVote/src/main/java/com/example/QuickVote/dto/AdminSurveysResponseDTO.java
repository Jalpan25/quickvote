package com.example.QuickVote.dto;

import java.util.List;

public class AdminSurveysResponseDTO {
    private AdminDTO admin;
    private List<CreatedSurveyDTO> createdSurveys;

    public AdminSurveysResponseDTO(AdminDTO admin, List<CreatedSurveyDTO> createdSurveys) {
        this.admin = admin;
        this.createdSurveys = createdSurveys;
    }

    public AdminDTO getAdmin() { return admin; }
    public void setAdmin(AdminDTO admin) { this.admin = admin; }

    public List<CreatedSurveyDTO> getCreatedSurveys() { return createdSurveys; }
    public void setCreatedSurveys(List<CreatedSurveyDTO> createdSurveys) { this.createdSurveys = createdSurveys; }
}
