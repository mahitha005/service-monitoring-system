package com.example.pack.sms.controller;

import com.example.pack.sms.dto.MetricDTO;
import com.example.pack.sms.service.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metrics")
@RequiredArgsConstructor
public class MetricController {

    private final MetricService metricService;

    @PostMapping
    public String addMetric(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody MetricDTO metricDTO) {

        metricService.addMetricByApiKey(apiKey, metricDTO);

        return "Metric recorded successfully";
    }
}