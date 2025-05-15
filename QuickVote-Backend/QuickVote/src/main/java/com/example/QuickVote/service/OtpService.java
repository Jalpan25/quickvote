package com.example.QuickVote.service;

import com.example.QuickVote.model.Otp;
import com.example.QuickVote.repository.OtpRepository;
import com.example.QuickVote.util.EmailTemplates;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
        try {
            // Create a MIME message
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Your QuickVote Verification Code");

            // Get the email template from the EmailTemplates utility class
            String htmlContent = EmailTemplates.getOtpEmailTemplate(otp);

            helper.setText(htmlContent, true); // Set to HTML format

            // Send the email
            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }
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