package com.example.pack.sms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            log.info("Attempting to send OTP email to: {}", toEmail);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("OTP Verification - Monitoring System");
            message.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.");

            mailSender.send(message);
            log.info("OTP email sent successfully to: {}", toEmail);
            
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}. Error: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }

    public void sendAlertEmail(String to, String serviceName, String messageText) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("⚠ ALERT: Issue detected in " + serviceName);
            message.setText(
                "Service Monitoring Alert\n\n" +
                "Service: " + serviceName + "\n" +
                "Issue: " + messageText + "\n\n" +
                "Please check the monitoring dashboard."
            );

            mailSender.send(message);
            log.info("Alert email sent successfully to: {}", to);
            
        } catch (Exception e) {
            log.error("Failed to send alert email to: {}. Error: {}", to, e.getMessage(), e);
        }
    }
}