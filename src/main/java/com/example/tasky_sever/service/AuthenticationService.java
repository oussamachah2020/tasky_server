package com.example.tasky_sever.service;

import com.example.tasky_sever.model.AuthenticationReponse;
import com.example.tasky_sever.model.User;
import com.example.tasky_sever.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationReponse register(User req) {
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());

        user = repository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationReponse(token);
    }

    public AuthenticationReponse authenticate(User req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        User user = repository.findByUsername(req.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthenticationReponse(token);
    }
}
