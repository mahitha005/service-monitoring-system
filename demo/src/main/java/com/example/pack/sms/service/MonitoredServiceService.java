package com.example.pack.sms.service;

import com.example.pack.sms.entity.MonitoredService;
import com.example.pack.sms.repository.MonitoredServiceRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoredServiceService {

    private final MonitoredServiceRepository repository;

    public MonitoredService addService(MonitoredService service) {
        service.setHealthScore(100.0);
        service.setStatus("HEALTHY");
        return repository.save(service);
    }

    public List<MonitoredService> getAllServices() {
        return repository.findAll();
    }
}

