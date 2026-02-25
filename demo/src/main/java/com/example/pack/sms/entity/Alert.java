package com.example.pack.sms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String message;

    private String status; // OPEN, ACKNOWLEDGED, RESOLVED

    private int count;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private MonitoredService service;

}



