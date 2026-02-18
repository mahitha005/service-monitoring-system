package com.example.pack.sms.repository;

import com.example.pack.sms.entity.MonitoredService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredServiceRepository
        extends JpaRepository<MonitoredService, Long> {
}
