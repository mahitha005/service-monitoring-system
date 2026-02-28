package com.example.pack.sms.dto;

import lombok.Data;

@Data
public class VerifyRequest {
    private String username;
    private String otp;
}