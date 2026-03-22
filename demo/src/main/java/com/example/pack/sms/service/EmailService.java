package com.example.pack.sms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;
    
    @Value("${app.from.email}")
    private String fromEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            log.info("Attempting to send OTP email to: {}", toEmail);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> emailData = new HashMap<>();
            emailData.put("from", fromEmail);
            emailData.put("to", List.of(toEmail));
            emailData.put("subject", "OTP Verification - Monitoring System");
            emailData.put("html", "<h2>Your OTP Code</h2><p>Your OTP is: <strong>" + otp + "</strong></p><p>It is valid for 5 minutes.</p>");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.resend.com/emails", 
                request, 
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("OTP email sent successfully to: {}", toEmail);
            } else {
                throw new RuntimeException("Email API returned status: " + response.getStatusCode());
            }
            
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}. Error: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }

    public void sendAlertEmail(String to, String serviceName, String messageText) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> emailData = new HashMap<>();
            emailData.put("from", fromEmail);
            emailData.put("to", List.of(to));
            emailData.put("subject", "⚠ ALERT: Issue detected in " + serviceName);
            emailData.put("html", "<h2>Service Monitoring Alert</h2>" +
                         "<p><strong>Service:</strong> " + serviceName + "</p>" +
                         "<p><strong>Issue:</strong> " + messageText + "</p>" +
                         "<p>Please check the monitoring dashboard.</p>");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);
            
            ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.resend.com/emails", 
                request, 
                Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Alert email sent successfully to: {}", to);
            }
            
        } catch (Exception e) {
            log.error("Failed to send alert email to: {}. Error: {}", to, e.getMessage(), e);
        }
    }
}