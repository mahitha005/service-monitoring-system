package com.example.pack.sms.service;

import com.example.pack.sms.entity.MonitoredService;
import com.example.pack.sms.entity.User;
import com.example.pack.sms.repository.MonitoredServiceRepository;
import com.example.pack.sms.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MonitoredServiceService {

    private final MonitoredServiceRepository repository;
    private final UserRepository userRepository;

    public MonitoredService addService(MonitoredService service,
                                       Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        service.setUser(user);
        service.setHealthScore(100.0);
        service.setStatus("HEALTHY");
        service.setApiKey(UUID.randomUUID().toString());

        return repository.save(service);
    }

    public List<MonitoredService> getAllServices(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repository.findByUser(user);
    }

    public void updateHealth(MonitoredService service, double health) {

        service.setHealthScore(health);

        if (health >= 90) {
            service.setStatus("HEALTHY");
        } else if (health >= 60) {
            service.setStatus("DEGRADED");
        } else {
            service.setStatus("UNHEALTHY");
        }

        repository.save(service);
    }
}