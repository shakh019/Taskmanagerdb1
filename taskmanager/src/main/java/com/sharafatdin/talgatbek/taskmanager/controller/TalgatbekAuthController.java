package com.sharafatdin.talgatbek.taskmanager.controller;

import com.sharafatdin.talgatbek.taskmanager.dto.request.*;
import com.sharafatdin.talgatbek.taskmanager.dto.response.*;
import com.sharafatdin.talgatbek.taskmanager.service.TalgatbekAuthService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/** @author Talgatbek Sharafatdin */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Auth endpoints by Talgatbek Sharafatdin")
public class TalgatbekAuthController {

    private final TalgatbekAuthService authService;

    @Operation(summary = "Register new user")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", authService.register(request)));
    }

    @Operation(summary = "Login user")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Login successful", authService.login(request)));
    }
}
