package com.example.pack.sms.repository;

import com.example.pack.sms.entity.MonitoredService;
import com.example.pack.sms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MonitoredServiceRepository
        extends JpaRepository<MonitoredService, Long> {

    Optional<MonitoredService> findByApiKey(String apiKey);

    long countByStatus(String status);

    List<MonitoredService> findByUser(User user);

    long countByUser(User user);

    long countByUserAndStatus(User user, String status);
}