package com.example.pack.sms.service;

import com.resend.*;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final Resend resend;
    
    @Value("${app.from.email}")
    private String fromEmail;

    public EmailService(@Value("${resend.api.key}") String apiKey) {
        this.resend = new Resend(apiKey);
    }

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            log.info("Attempting to send OTP email to: {}", toEmail);
            
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                .from(fromEmail)
                .to(toEmail)
                .subject("OTP Verification - Monitoring System")
                .html("<h2>Your OTP Code</h2><p>Your OTP is: <strong>" + otp + "</strong></p><p>It is valid for 5 minutes.</p>")
                .build();

            SendEmailResponse response = resend.emails().send(emailRequest);
            log.info("OTP email sent successfully to: {}. ID: {}", toEmail, response.getId());
            
        } catch (ResendException e) {
            log.error("Failed to send OTP email to: {}. Error: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }

    public void sendAlertEmail(String to, String serviceName, String messageText) {
        try {
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                .from(fromEmail)
                .to(to)
                .subject("⚠ ALERT: Issue detected in " + serviceName)
                .html("<h2>Service Monitoring Alert</h2>" +
                     "<p><strong>Service:</strong> " + serviceName + "</p>" +
                     "<p><strong>Issue:</strong> " + messageText + "</p>" +
                     "<p>Please check the monitoring dashboard.</p>")
                .build();

            SendEmailResponse response = resend.emails().send(emailRequest);
            log.info("Alert email sent successfully to: {}. ID: {}", to, response.getId());
            
        } catch (ResendException e) {
            log.error("Failed to send alert email to: {}. Error: {}", to, e.getMessage(), e);
        }
    }
}