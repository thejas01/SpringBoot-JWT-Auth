package com.ems.controller;

import com.ems.config.JwtTokenProvider;
import com.ems.dto.LoginRequest;
import com.ems.dto.LoginResponse;
import com.ems.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validate user credentials via the service
        boolean isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (isAuthenticated) {
            String token = jwtTokenProvider.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(new LoginResponse(token, "Login successful"));
        }

        return ResponseEntity.status(401).body(new LoginResponse(null, "Invalid credentials"));
    }
}
