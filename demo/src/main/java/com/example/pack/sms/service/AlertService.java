package com.example.pack.sms.service;

import com.example.pack.sms.entity.Alert;
import com.example.pack.sms.repository.AlertRepository;

public class AlertService {
    AlertRepository alertRepository;
    public Alert acknowledgeAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus("ACKNOWLEDGED");
        return alertRepository.save(alert);
    }

    public Alert resolveAlert(Long id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setStatus("RESOLVED");
        return alertRepository.save(alert);
    }
}
