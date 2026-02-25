package com.example.pack.sms.service;

import com.example.pack.sms.entity.MonitoredService;
import com.example.pack.sms.repository.MonitoredServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoredServiceService {

    private final MonitoredServiceRepository repository;



    public MonitoredService addService(MonitoredService service) {

        service.setHealthScore(100.0);
        service.setStatus("HEALTHY");

        // Generate API Key
        service.setApiKey(UUID.randomUUID().toString());

        return repository.save(service);
    }

    public List<MonitoredService> getAllServices() {
        return repository.findAll();
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
