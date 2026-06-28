package com.example.QuickVote.dto;

public class CreatedSurveyDTO {
    private String name;
    private String id;
    private String createdDate;

    public CreatedSurveyDTO(String name, String id, String createdDate) {
        this.name = name;
        this.id = id;
        this.createdDate = createdDate;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
}
