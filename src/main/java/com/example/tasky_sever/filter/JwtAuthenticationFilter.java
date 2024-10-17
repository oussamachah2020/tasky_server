package com.example.tasky_sever.filter;

import com.example.tasky_sever.service.auth.JwtService;
import com.example.tasky_sever.service.auth.UserDetailsImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsImplementation userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsImplementation userDetails) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetails;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Get the request URI to check for public endpoints
        String requestURI = request.getRequestURI();

        // Skip JWT processing for public endpoints like /login, /register, and /refresh
        if (requestURI.startsWith("/login") || requestURI.startsWith("/register") || requestURI.startsWith("/refresh")) {
            // Allow the request to proceed without JWT validation
            filterChain.doFilter(request, response);
            return;
        }

        // Proceed with JWT validation for other endpoints
        String authHeader = request.getHeader("Authorization");

        // Check if the Authorization header is missing or doesn't start with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;  // Exit the method to prevent further processing
        }

        // Extract the token from the Authorization header
        String token = authHeader.substring(7); // "Bearer " is 7 characters long

        // Extract the username from the token
        String username = jwtService.extractUsername(token);

        // Load user details
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validate the token
            if (jwtService.isValid(token, userDetails)) {
                // Create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }


}
