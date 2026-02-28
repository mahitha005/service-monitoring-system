package com.example.pack.sms.controller;

import com.example.pack.sms.entity.MonitoredService;
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

    @PostMapping
    public MonitoredService addService(@RequestBody MonitoredService s,
                                       Authentication authentication) {
        return service.addService(s, authentication);
    }

    @GetMapping
    public List<MonitoredService> getAll(Authentication authentication) {
        return service.getAllServices(authentication);
    }
}