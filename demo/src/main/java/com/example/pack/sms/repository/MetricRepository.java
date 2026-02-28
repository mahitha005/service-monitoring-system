package com.example.pack.sms.repository;

import com.example.pack.sms.entity.Metric;
import com.example.pack.sms.entity.MonitoredService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricRepository extends JpaRepository<Metric, Long> {
    long countByServiceAndResponseTimeGreaterThan(
            MonitoredService service,
            Double threshold
    );
}
