package com.example.pack.sms.controller;

import com.example.pack.sms.entity.MonitoredService;
import com.example.pack.sms.entity.MonitoringRule;
import com.example.pack.sms.repository.MonitoredServiceRepository;
import com.example.pack.sms.repository.MonitoringRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class MonitoringRuleController {

    private final MonitoringRuleRepository ruleRepository;
    private final MonitoredServiceRepository serviceRepository;

    @PostMapping("/{serviceId}")
    public String createRule(@PathVariable Long serviceId,
                             @RequestBody MonitoringRule rule) {

        MonitoredService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        rule.setService(service);
        ruleRepository.save(rule);

        return "Rule configured successfully";
    }

    @GetMapping("/{serviceId}")
    public MonitoringRule getRule(@PathVariable Long serviceId) {

        MonitoredService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        return ruleRepository.findByService(service)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }
}
