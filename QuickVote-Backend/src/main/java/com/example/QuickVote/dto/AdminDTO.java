package com.example.QuickVote.dto;

public class AdminDTO {
    private String email;
    private String role = "Administrator"; // Default role

    public AdminDTO(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
