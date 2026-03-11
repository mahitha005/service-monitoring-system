package com.example.pack.sms.service;

import com.example.pack.sms.dto.MetricDTO;
import com.example.pack.sms.entity.*;
import com.example.pack.sms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetricService {

    private final MetricRepository metricRepository;
    private final MonitoredServiceRepository serviceRepository;
    private final AlertRepository alertRepository;
    private final MonitoringRuleRepository ruleRepository;
    private final MonitoredServiceService monitoredServiceService;
    private final EmailService emailService;

    public void addMetricByApiKey(String apiKey, MetricDTO metricDTO) {

        MonitoredService service = serviceRepository
                .findByApiKey(apiKey)
                .orElseThrow(() -> new RuntimeException("Invalid API Key"));

        processMetric(service, metricDTO);
    }

    private void processMetric(MonitoredService service, MetricDTO metricDTO) {

        // 1️⃣ Save metric
        Metric metric = new Metric();

        metric.setService(service);
        metric.setResponseTime(metricDTO.getResponseTime());
        metric.setFailureCount(metricDTO.getFailureCount());
        metric.setTimestamp(LocalDateTime.now());

        metricRepository.save(metric);

        // 2️⃣ Calculate health
        double health = 100
                - (metricDTO.getResponseTime() / 10)
                - (metricDTO.getFailureCount() * 5);

        if (health < 0) health = 0;

        monitoredServiceService.updateHealth(service, health);

        // 3️⃣ Apply monitoring rules
        List<MonitoringRule> rules = ruleRepository.findByService(service);

        for (MonitoringRule rule : rules) {

            double actualValue = switch (rule.getMetricType()) {
                case "RESPONSE_TIME" -> metricDTO.getResponseTime();
                case "FAILURE_COUNT" -> (double) metricDTO.getFailureCount();
                case "HEALTH" -> health;
                default -> 0;
            };

            // ❌ If threshold condition not satisfied → skip
            if (!evaluate(actualValue, rule.getOperator(), rule.getThreshold())) {
                continue;
            }

            // 4️⃣ Increase breach counter
            int breachCount = rule.getCurrentBreachCount() == null
                    ? 0
                    : rule.getCurrentBreachCount();

            breachCount++;

            rule.setCurrentBreachCount(breachCount);

            ruleRepository.save(rule);

            Integer limit = rule.getBreachCountLimit();

            // 🚨 Only create alert when limit reached
            if (limit != null && breachCount >= limit) {

                createOrUpdateAlert(
                        service,
                        rule.getMetricType(),
                        rule.getMetricType() + " threshold breached"
                );
            }
        }
    }

    private boolean evaluate(double actualValue, String operator, double threshold) {

        return switch (operator) {
            case ">" -> actualValue > threshold;
            case "<" -> actualValue < threshold;
            case ">=" -> actualValue >= threshold;
            case "<=" -> actualValue <= threshold;
            case "==" -> actualValue == threshold;
            default -> false;
        };
    }

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

            // Alert already exists → increase count
            Alert alert = existingAlert.get();

            alert.setCount(alert.getCount() + 1);
            alert.setCreatedAt(LocalDateTime.now());

            alertRepository.save(alert);

        } else {

            // Create new alert
            Alert alert = new Alert();

            alert.setService(service);
            alert.setType(type);
            alert.setMessage(message);
            alert.setStatus("OPEN");
            alert.setCount(1);
            alert.setCreatedAt(LocalDateTime.now());

            alertRepository.save(alert);

            // 📧 Send email notification
            emailService.sendAlertEmail(
                    service.getUser().getEmail(),
                    service.getServiceName(),
                    message
            );
        }
    }
}