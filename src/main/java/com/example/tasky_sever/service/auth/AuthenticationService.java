package com.example.tasky_sever.service.auth;

import com.example.tasky_sever.model.auth.AuthenticationResponse;
import com.example.tasky_sever.model.auth.User;
import com.example.tasky_sever.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);



    private final AuthenticationManager authenticationManager;


    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, UserDetailsImplementation userDetailsService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public Object register(User req) {
        // Check if the user already exists
        Optional<User> existingUser = repository.findByUsername(req.getUsername());

        if (existingUser.isPresent()) {
            return "User already exists";
        }

        // Create a new user if not already present
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(req.getRole());

        // Save the new user
        user = repository.save(user);

        // Generate tokens for the new user
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Return the tokens in the authentication response
        return new AuthenticationResponse(accessToken, refreshToken);
    }


    public AuthenticationResponse authenticate(User req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        User user = repository.findByUsername(req.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    public String refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = repository.findByUsername(username).orElseThrow();

        return jwtService.generateAccessToken(user);
    }

    public Optional<UserDto> getUser(String token) {
        if (!token.isEmpty()) {
            String username = jwtService.extractUsername(token);
            return repository.findByUsername(username).map((user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getRole())));
        } else {
            return Optional.empty();
        }
    }
}
