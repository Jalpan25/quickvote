package com.example.QuickVote.dto;

public class AdminRequestDTO {
    private String institutionName;
    private String email;
    private String phoneNumber;
    private String password;
    private String fixedDomain;

    // Getters and Setters
    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFixedDomain() {
        return fixedDomain;
    }

    public void setFixedDomain(String fixedDomain) {
        this.fixedDomain = fixedDomain;
    }
}
