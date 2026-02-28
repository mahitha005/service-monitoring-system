package com.example.pack.sms.controller;

import com.example.pack.sms.entity.*;
import com.example.pack.sms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
@RequiredArgsConstructor
public class MonitoringRuleController {

    private final MonitoringRuleRepository ruleRepository;
    private final MonitoredServiceRepository serviceRepository;
    private final UserRepository userRepository;

    @PostMapping("/{serviceId}")
    public MonitoringRule createRule(@PathVariable Long serviceId,
                                     @RequestBody MonitoringRule rule,
                                     Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MonitoredService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!service.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        rule.setService(service);
        return ruleRepository.save(rule);
    }

    @GetMapping("/{serviceId}")
    public List<MonitoringRule> getRules(@PathVariable Long serviceId,
                                         Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        MonitoredService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        if (!service.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return ruleRepository.findByService(service);
    }
}