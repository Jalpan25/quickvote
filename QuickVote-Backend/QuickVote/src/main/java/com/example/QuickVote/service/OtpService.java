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
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpRepository.findByEmail(email).ifPresent(otpRepository::delete);

        Otp otpEntity = new Otp(email, otp, LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);

        sendOtpEmail(email, otp);
        return otp;
    }

    private void sendOtpEmail(String email, String otp) {
        String subject = "Your QuickVote Verification Code";
        String htmlContent = EmailTemplates.getOtpEmailTemplate(otp);
        sendHtmlMail(email, subject, htmlContent);
    }

    public boolean verifyOtp(String email, String enteredOtp) {
        Optional<Otp> otpEntity = otpRepository.findByEmail(email);
        return otpEntity.isPresent() &&
                otpEntity.get().getOtp().equals(enteredOtp) &&
                otpEntity.get().getExpirationTime().isAfter(LocalDateTime.now());
    }

    // ✅ New method to send HTML emails
    public void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // Enable HTML content

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace(); // You may replace this with a logger
        }
    }
}
