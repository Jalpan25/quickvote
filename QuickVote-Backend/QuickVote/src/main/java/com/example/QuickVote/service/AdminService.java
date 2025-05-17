package com.example.QuickVote.service;

import com.example.QuickVote.dto.AdminRequestDTO;
import com.example.QuickVote.dto.PendingAdminResponseDTO;
import com.example.QuickVote.model.Admin;
import com.example.QuickVote.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Using BCryptPasswordEncoder for both password encryption and validation
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Method for creating an admin (called when a user requests to become an admin)
    public Admin createAdmin(AdminRequestDTO adminRequestDTO) {
        Admin admin = new Admin.Builder()
                .institutionName(adminRequestDTO.getInstitutionName())
                .email(adminRequestDTO.getEmail())
                .phoneNumber(adminRequestDTO.getPhoneNumber())
                .password(passwordEncoder.encode(adminRequestDTO.getPassword())) // Encrypt password
                .fixedDomain(adminRequestDTO.getFixedDomain())
                .role("pending") // Default role is "pending"
                .build();

        return adminRepository.save(admin);
    }

    // Method to find an admin by email (called when admin tries to log in)
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    // Method to check if the provided password matches the stored hashed password
    public boolean checkPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }



    // Fetch all pending admin requests
    public List<PendingAdminResponseDTO> getPendingAdmins() {
        List<Admin> pendingAdmins = adminRepository.findByRole("pending");

        // Convert Admin objects to DTOs to exclude sensitive data
        return pendingAdmins.stream()
                .map(admin -> new PendingAdminResponseDTO(
                        admin.getInstitutionName(),
                        admin.getEmail(),
                        admin.getPhoneNumber(),
                        admin.getFixedDomain()))
                .collect(Collectors.toList());
    }
    public List<PendingAdminResponseDTO> getApprovedAdmins() {
        List<Admin> approvedAdmins = adminRepository.findByRole("admin");

        return approvedAdmins.stream()
                .map(admin -> new PendingAdminResponseDTO(
                        admin.getInstitutionName(),
                        admin.getEmail(),
                        admin.getPhoneNumber(),
                        admin.getFixedDomain()))
                .collect(Collectors.toList());
    }



    @Transactional
    public String processAdminRequest(String email, String status) {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);

        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();

            if ("accepted".equalsIgnoreCase(status)) {
                // Update role to "admin"
                admin.setRole("admin");
                adminRepository.save(admin);
                return "Admin request approved. Role updated to 'admin'.";
            }
            else if ("rejected".equalsIgnoreCase(status)) {
                // Delete admin request
                adminRepository.deleteByEmail(email);
                return "Admin request rejected. User removed.";
            }
            else {
                return "Invalid status. Use 'accepted' or 'rejected'.";
            }
        }
        else {
            return "No admin request found for this email.";
        }
    }

    public List<Admin> findByRole(String role) {
        return adminRepository.findByRole(role);
    }

    public String getFixedDomainByEmail(String email) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        return admin.map(Admin::getFixedDomain).orElse(null);
    }
}
