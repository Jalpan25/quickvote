package com.example.QuickVote.model;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String institutionName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String fixedDomain;

    @Column(nullable = false)
    private String role; // Changed from Enum to String

    // Default Constructor
    public Admin() {}

    // Parameterized Constructor
    public Admin(Long id, String institutionName, String email, String phoneNumber, String password, String fixedDomain, String role) {
        this.id = id;
        this.institutionName = institutionName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.fixedDomain = fixedDomain;
        this.role = role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Manual Builder Pattern
    public static class Builder {
        private Long id;
        private String institutionName;
        private String email;
        private String phoneNumber;
        private String password;
        private String fixedDomain;
        private String role;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder institutionName(String institutionName) {
            this.institutionName = institutionName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder fixedDomain(String fixedDomain) {
            this.fixedDomain = fixedDomain;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Admin build() {
            return new Admin(id, institutionName, email, phoneNumber, password, fixedDomain, role);
        }
    }
}
