package org.hotwax.hotwaxassignment.controller;

import jakarta.validation.Valid;
import org.hotwax.hotwaxassignment.dto.request.LoginRequest;
import org.hotwax.hotwaxassignment.dto.request.SignupRequest;
import org.hotwax.hotwaxassignment.dto.response.AuthResponse;
import org.hotwax.hotwaxassignment.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        AuthResponse response = authService.signup(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

