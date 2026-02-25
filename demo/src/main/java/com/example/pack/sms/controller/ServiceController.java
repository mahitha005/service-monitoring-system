package com.example.pack.sms.controller;

import com.example.pack.sms.entity.MonitoredService;
import com.example.pack.sms.entity.User;
import com.example.pack.sms.repository.MonitoredServiceRepository;
import com.example.pack.sms.repository.UserRepository;
import com.example.pack.sms.service.MonitoredServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {

    private final MonitoredServiceService service;
    private final UserRepository userRepository;
    private final MonitoredServiceRepository serviceRepository;

    @PostMapping
    public MonitoredService addService(@RequestBody MonitoredService s,
                                       Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        s.setUser(user);

        return service.addService(s);
    }

    @GetMapping
    public List<MonitoredService> getAll(Authentication authentication) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return serviceRepository.findByUser(user);
    }
}