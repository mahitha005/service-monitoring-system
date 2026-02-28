package com.example.pack.sms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metricType;
    private String operator;
    private Double threshold;

    private Integer breachCountLimit;  // when to alert

    private Integer currentBreachCount = 0;  // 🔥 NEW

    @ManyToOne
    @JoinColumn(name = "service_id")
    private MonitoredService service;
}