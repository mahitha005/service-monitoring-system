package com.example.pack.sms.controller;

import com.example.pack.sms.entity.Alert;
import com.example.pack.sms.entity.User;
import com.example.pack.sms.repository.AlertRepository;
import com.example.pack.sms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    @GetMapping
    public List<Alert> getAllAlerts(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return alertRepository.findByServiceUser(user);
    }

    @PutMapping("/{id}/ack")
    public String acknowledge(@PathVariable Long id) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus("ACKNOWLEDGED");
        alertRepository.save(alert);

        return "Alert acknowledged";
    }

    @PutMapping("/{id}/resolve")
    public String resolve(@PathVariable Long id) {

        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus("RESOLVED");
        alertRepository.save(alert);

        return "Alert resolved";
    }
}