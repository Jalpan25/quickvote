package com.example.QuickVote.service;

import com.example.QuickVote.model.Otp;
import com.example.QuickVote.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String generateOtp(String email) {
        // Generate a 6-digit OTP
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        // Check if an OTP already exists for the email
        Optional<Otp> existingOtp = otpRepository.findByEmail(email);

        // If an OTP exists, delete it
        existingOtp.ifPresent(otpRepository::delete);

        // Create a new OTP entity and save it
        Otp otpEntity = new Otp(email, otp, LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);

        // Send the OTP to the user's email
        sendOtpEmail(email, otp);
        return otp;
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
    }

    public boolean verifyOtp(String email, String enteredOtp) {
        // Fetch the OTP entity by email
        Optional<Otp> otpEntity = otpRepository.findByEmail(email);

        // Validate the OTP and check its expiration time
        if (otpEntity.isPresent()) {
            Otp otp = otpEntity.get();
            if (otp.getOtp().equals(enteredOtp) && otp.getExpirationTime().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }
}
