package com.mini.project.library_muhriz.controllers;

import com.mini.project.library_muhriz.dto.request.LoginRequest;
import com.mini.project.library_muhriz.dto.request.RegisterRequest;
import com.mini.project.library_muhriz.services.AuthService;
import com.mini.project.library_muhriz.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            String result = authService.register(request);
            return ResponseEntity.ok(ApiResponse.success("User berhasil didaftarkan", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success("Login berhasil", token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(401, e.getMessage()));
        }
    }
}


