package com.example.pack.sms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    @Value("${emailjs.service.id}")
    private String serviceId;
    
    @Value("${emailjs.otp.template.id}")
    private String otpTemplateId;
    
    @Value("${emailjs.alert.template.id}")
    private String alertTemplateId;
    
    @Value("${emailjs.public.key}")
    private String publicKey;
    
    @Value("${emailjs.private.key}")
    private String privateKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendOtpEmail(String toEmail, String otp) {
        try {
            log.info("Attempting to send OTP email to: {}", toEmail);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> emailData = new HashMap<>();
            emailData.put("service_id", serviceId);
            emailData.put("template_id", otpTemplateId);
            emailData.put("user_id", publicKey);
            emailData.put("accessToken", privateKey);
            
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("email", toEmail);
            templateParams.put("otp_code", otp);
            
            emailData.put("template_params", templateParams);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.emailjs.com/api/v1.0/email/send", 
                request, 
                String.class
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
            log.info("Attempting to send alert email to: {}", to);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> emailData = new HashMap<>();
            emailData.put("service_id", serviceId);
            emailData.put("template_id", alertTemplateId);
            emailData.put("user_id", publicKey);
            emailData.put("accessToken", privateKey);
            
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("email", to);
            templateParams.put("subject", "⚠ ALERT: Issue detected in " + serviceName);
            templateParams.put("message", "Service Monitoring Alert\n\n" +
                                        "Service: " + serviceName + "\n" +
                                        "Issue: " + messageText + "\n\n" +
                                        "Please check the monitoring dashboard.");
            
            emailData.put("template_params", templateParams);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(emailData, headers);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.emailjs.com/api/v1.0/email/send", 
                request, 
                String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("Alert email sent successfully to: {}", to);
            }
            
        } catch (Exception e) {
            log.error("Failed to send alert email to: {}. Error: {}", to, e.getMessage(), e);
        }
    }
}