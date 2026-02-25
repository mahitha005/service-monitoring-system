package com.example.pack.sms.service;

import com.example.pack.sms.dto.DashboardSummary;
import com.example.pack.sms.entity.User;
import com.example.pack.sms.repository.AlertRepository;
import com.example.pack.sms.repository.MonitoredServiceRepository;
import com.example.pack.sms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MonitoredServiceRepository serviceRepository;
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public DashboardSummary getSummary(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long total = serviceRepository.countByUser(user);

        long healthy = serviceRepository.countByUserAndStatus(user, "HEALTHY");

        long unhealthy = serviceRepository.countByUserAndStatus(user, "UNHEALTHY");

        long activeAlerts =
                alertRepository.countByServiceUserAndStatus(user, "OPEN");

        return new DashboardSummary(
                total,
                healthy,
                unhealthy,
                activeAlerts
        );
    }
}