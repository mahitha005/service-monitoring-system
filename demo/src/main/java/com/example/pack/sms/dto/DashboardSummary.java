package com.example.pack.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummary {

    private long totalServices;
    private long healthyServices;
    private long unhealthyServices;
    private long activeAlerts;
}