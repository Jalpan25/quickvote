package com.example.QuickVote.controller;

import com.example.QuickVote.dto.LoginRequestDTO;
import com.example.QuickVote.dto.AdminRequestDTO;
import com.example.QuickVote.dto.PendingAdminResponseDTO;
import com.example.QuickVote.dto.AppUser;
import com.example.QuickVote.model.Admin;
import com.example.QuickVote.service.AdminService;
import com.example.QuickVote.service.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @GetMapping("/secure")
//    public ResponseEntity<String> testSecureEndpoint() {
//        return ResponseEntity.ok("✅ Token is valid. You accessed a protected endpoint.");
//    }

    // ✅ Admin Signup Request
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> createAdmin(@RequestBody AdminRequestDTO adminRequestDTO) {
        adminService.createAdmin(adminRequestDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Your request to become an admin has been received.");
        return ResponseEntity.ok(response);
    }

    // ✅ Admin Login With JWT Token (using AuthenticationManager)
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO loginRequest) {
        Map<String, String> response = new HashMap<>();

        try {
            // Let Spring validate credentials via UserDetailsService
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            response.put("status", "error");
            response.put("message", "Incorrect email or password.");
            return ResponseEntity.status(401).body(response);
        }

        Optional<Admin> adminOptional = adminService.findByEmail(loginRequest.getEmail());

        if (adminOptional.isEmpty()) {
            response.put("status", "error");
            response.put("message", "Email not registered.");
            return ResponseEntity.status(404).body(response);
        }

        Admin admin = adminOptional.get();

        if ("pending".equalsIgnoreCase(admin.getRole())) {
            response.put("status", "error");
            response.put("message", "Your admin request is still pending approval.");
            return ResponseEntity.status(403).body(response);
        }

        // ✅ Generate JWT after successful authentication
        AppUser appUser = new AppUser(admin.getEmail(), admin.getRole());
        String jwtToken = jwtService.generateToken(appUser);

        response.put("status", "success");
        response.put("role", admin.getRole());
        response.put("token", jwtToken);

        return ResponseEntity.ok(response);
    }

    // ✅ View pending admin requests (no JWT required)
    @GetMapping("/pending")
    public ResponseEntity<List<PendingAdminResponseDTO>> getPendingAdmins() {
        return ResponseEntity.ok(adminService.getPendingAdmins());
    }

    // ✅ View approved admins (no JWT required)
    @GetMapping("/approved")
    public ResponseEntity<List<PendingAdminResponseDTO>> getApprovedAdmins() {
        return ResponseEntity.ok(adminService.getApprovedAdmins());
    }

    // ✅ Approve/reject admin requests (no JWT required)
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processAdminRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String status = request.get("status");

        String message = adminService.processAdminRequest(email, status);
        return ResponseEntity.ok(Map.of("message", message));
    }

    // ✅ Get fixed domain by email (no JWT required)
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
