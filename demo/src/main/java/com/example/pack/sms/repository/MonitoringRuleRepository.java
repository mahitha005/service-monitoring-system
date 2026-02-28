package com.example.pack.sms.repository;

import com.example.pack.sms.entity.MonitoringRule;
import com.example.pack.sms.entity.MonitoredService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoringRuleRepository extends JpaRepository<MonitoringRule, Long> {

    List<MonitoringRule> findByService(MonitoredService service);
}