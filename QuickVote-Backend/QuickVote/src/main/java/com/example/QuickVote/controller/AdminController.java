package com.example.QuickVote.controller;

import com.example.QuickVote.dto.LoginRequestDTO;
import com.example.QuickVote.dto.AdminRequestDTO;
import com.example.QuickVote.dto.PendingAdminResponseDTO;
import com.example.QuickVote.service.AdminService;
import com.example.QuickVote.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admins")  // Updated mapping to '/api/admins'
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Endpoint to create admin requests (user wants to become an admin)
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> createAdmin(@RequestBody AdminRequestDTO adminRequestDTO) {
        adminService.createAdmin(adminRequestDTO);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Your request to become an admin has been received.");

        return ResponseEntity.ok(response);
    }

    // New endpoint for admin login
    @PostMapping("/login")  // Admin login
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {
        // Now using Optional<Admin> for safe handling of null cases
        Optional<Admin> adminOptional = adminService.findByEmail(loginRequest.getEmail());

        Map<String, String> response = new HashMap<>();

        if (adminOptional.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Email not registered. Please check your email.");
            return ResponseEntity.status(404).body(response);
        }

        Admin admin = adminOptional.get(); // Safe to get the admin now

        if (!adminService.checkPassword(loginRequest.getPassword(), admin.getPassword())) {
            response.put("status", "error");
            response.put("message", "Incorrect password. Please try again.");
            return ResponseEntity.status(401).body(response);
        }

        if ("pending".equalsIgnoreCase(admin.getRole())) {
            response.put("status", "error");
            response.put("message", "Your admin request is still pending approval.");
            return ResponseEntity.status(403).body(response);
        }

        response.put("status", "success");
        response.put("role", admin.getRole());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PendingAdminResponseDTO>> getPendingAdmins() {
        List<PendingAdminResponseDTO> pendingAdmins = adminService.getPendingAdmins();
        return ResponseEntity.ok(pendingAdmins);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<PendingAdminResponseDTO>> getApprovedAdmins() {
        List<PendingAdminResponseDTO> admins = adminService.getApprovedAdmins();
        return ResponseEntity.ok(admins);
    }

    // API to approve or reject admin requests
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processAdminRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String status = request.get("status");

        String message = adminService.processAdminRequest(email, status);

        // Create response JSON
        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/getFixedDomain")
    public ResponseEntity<?> getFixedDomain(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email is required"));
        }

        String fixedDomain = adminService.getFixedDomainByEmail(email);

        if (fixedDomain == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Admin not found"));
        }

        return ResponseEntity.ok(Map.of("fixedDomain", fixedDomain));
    }
}
