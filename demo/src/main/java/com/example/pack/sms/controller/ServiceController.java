package com.example.pack.sms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    @GetMapping("/services")
    public String getServices() {
        return "Protected API working ✅";
    }
}
