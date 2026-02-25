package com.example.pack.sms.service;

import com.example.pack.sms.entity.*;
import com.example.pack.sms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;
    private final MonitoredServiceRepository serviceRepository;
    private final AlertRepository alertRepository;
    private final MonitoringRuleRepository ruleRepository;
    private final EmailService emailService;
    private final MonitoredServiceService monitoredServiceService;

    public void addMetricByApiKey(String apiKey, Metric metric) {

        MonitoredService service = serviceRepository.findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        MonitoringRule rule = ruleRepository.findByService(service)
                .orElseThrow(() -> new RuntimeException("Monitoring rule not configured"));

        metric.setService(service);
        metric.setTimestamp(LocalDateTime.now());
        metricRepository.save(metric);

        double health = 100
                - (metric.getResponseTime() / 10)
                - (metric.getFailureCount() * 5);

        if (health < 0) health = 0;

        monitoredServiceService.updateHealth(service, health);

        if (metric.getResponseTime() > rule.getMaxResponseTime()) {
            createOrUpdateAlert(service, "RESPONSE_TIME",
                    "Response time exceeded configured threshold");
        }

        if (metric.getFailureCount() > rule.getMaxFailureCount()) {
            createOrUpdateAlert(service, "FAILURE_COUNT",
                    "Failure count exceeded configured threshold");
        }

        if (health < rule.getMinHealthScore()) {
            createOrUpdateAlert(service, "LOW_HEALTH",
                    "Service health below configured threshold");
        }
    }

    public void addMetric(Long serviceId, Metric metric) {

        // 1️⃣ Get Service
        MonitoredService service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        // 2️⃣ Get Monitoring Rule
        MonitoringRule rule = ruleRepository.findByService(service)
                .orElseThrow(() -> new RuntimeException("Monitoring rule not configured"));

        // 3️⃣ Save Metric
        metric.setService(service);
        metric.setTimestamp(LocalDateTime.now());
        metricRepository.save(metric);

        // 4️⃣ Calculate Health
        double health = 100
                - (metric.getResponseTime() / 10)
                - (metric.getFailureCount() * 5);

        if (health < 0) {
            health = 0;
        }

        monitoredServiceService.updateHealth(service, health);

        // 5️⃣ Rule-based Alerts

        if (metric.getResponseTime() > rule.getMaxResponseTime()) {
            createOrUpdateAlert(service, "RESPONSE_TIME",
                    "Response time exceeded configured threshold");
        }

        if (metric.getFailureCount() > rule.getMaxFailureCount()) {
            createOrUpdateAlert(service, "FAILURE_COUNT",
                    "Failure count exceeded configured threshold");
        }

        if (health < rule.getMinHealthScore()) {
            createOrUpdateAlert(service, "LOW_HEALTH",
                    "Service health below configured threshold");
        }
    }

    // 🔥 Alert Deduplication Logic
    private void createOrUpdateAlert(
            MonitoredService service,
            String type,
            String message
    ) {

        Optional<Alert> existingAlert =
                alertRepository.findByServiceAndTypeAndStatus(
                        service,
                        type,
                        "OPEN"
                );

        if (existingAlert.isPresent()) {

            Alert alert = existingAlert.get();
            alert.setCount(alert.getCount() + 1);
            alert.setCreatedAt(LocalDateTime.now());

            alertRepository.save(alert);

        } else {

            Alert alert = new Alert();
            alert.setService(service);
            alert.setType(type);
            alert.setMessage(message);
            alert.setStatus("OPEN");
            alert.setCount(1);
            alert.setCreatedAt(LocalDateTime.now());

            alertRepository.save(alert);

// 🔥 SEND EMAIL
            String userEmail = service.getUser().getEmail();

            emailService.sendAlertEmail(
                    userEmail,
                    "🚨 Service Alert: " + service.getServiceName(),
                    "Alert Type: " + type +
                            "\nMessage: " + message +
                            "\nService: " + service.getServiceName()
            );
        }
    }
}