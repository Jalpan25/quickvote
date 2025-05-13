package com.example.QuickVote.controller;
import com.example.QuickVote.dto.AppUser;
import com.example.QuickVote.model.User;
import com.example.QuickVote.repository.UserRepository;
import com.example.QuickVote.service.JwtService;
import com.example.QuickVote.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        otpService.generateOtp(email);
        return ResponseEntity.ok("OTP sent to " + email);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        if (otpService.verifyOtp(email, otp)) {
            User user = userRepository.findByEmail(email).orElse(new User(email, true));
            user.setVerified(true);
            userRepository.save(user);

            // ✅ Generate JWT
            AppUser appUser = new AppUser(user.getEmail(), "USER");
            String jwtToken = jwtService.generateToken(appUser);

            // ✅ Return token to frontend
            return ResponseEntity.ok(Map.of("token", jwtToken));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP or OTP expired.");
    }

}
