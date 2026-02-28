package com.example.pack.sms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;   // 🔥 important

    public void sendOtpEmail(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);   // 🔥 THIS FIXES YOUR ERROR
        message.setTo(toEmail);
        message.setSubject("OTP Verification - Monitoring System");
        message.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.");

        mailSender.send(message);
    }
}