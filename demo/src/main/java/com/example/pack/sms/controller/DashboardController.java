package com.example.pack.sms.controller;

import com.example.pack.sms.dto.DashboardSummary;
import com.example.pack.sms.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardSummary getDashboard(Authentication authentication) {
        return dashboardService.getSummary(authentication);
    }
}