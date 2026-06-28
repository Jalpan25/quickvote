package com.example.QuickVote.dto;

public class PendingAdminResponseDTO {
    private String institutionName;
    private String email;
    private String phoneNumber;
    private String fixedDomain;

    public PendingAdminResponseDTO(String institutionName, String email, String phoneNumber, String fixedDomain) {
        this.institutionName = institutionName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fixedDomain = fixedDomain;
    }

    // Getters
    public String getInstitutionName() {
        return institutionName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFixedDomain() {
        return fixedDomain;
    }
}
