package com.example.pack.sms.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data   // 🔥 VERY IMPORTANT
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double responseTime;
    private int failureCount;
    private int successCount;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private MonitoredService service;
}
