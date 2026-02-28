package com.example.pack.sms.controller;

import com.example.pack.sms.dto.VerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.pack.sms.service.AuthService;
import com.example.pack.sms.entity.User;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/verify")
    public String verify(@RequestBody VerifyRequest request) {
        return authService.verifyOtp(
                request.getUsername(),
                request.getOtp()
        );
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return authService.login(user.getUsername(), user.getPassword());
    }
}
