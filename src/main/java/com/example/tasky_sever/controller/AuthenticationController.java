package com.example.tasky_sever.controller;

import com.example.tasky_sever.model.AuthenticationReponse;
import com.example.tasky_sever.model.User;
import com.example.tasky_sever.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationReponse> register(@RequestBody User req) {
        return ResponseEntity.ok(authenticationService.register(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationReponse> login(@RequestBody User req) {
        return ResponseEntity.ok(authenticationService.authenticate(req));
    }
}
