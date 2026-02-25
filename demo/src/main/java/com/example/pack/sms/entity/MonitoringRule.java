package com.example.pack.sms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonitoringRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double maxResponseTime;

    private int maxFailureCount;

    private double minHealthScore;

    @OneToOne
    @JoinColumn(name = "service_id")
    private MonitoredService service;
}