package com.example.pack.sms.dto;

import lombok.Data;

@Data
public class MetricDTO {

    private double responseTime;

    private int failureCount;

    private int successCount;
}
