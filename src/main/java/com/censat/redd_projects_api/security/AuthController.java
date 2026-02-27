package com.censat.redd_projects_api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtUtil jwtUtil;

    // Hardcoded credentials for simplicity
    private static final String HARDCODED_USERNAME = "admin";
    private static final String HARDCODED_PASSWORD = "password123";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        if (HARDCODED_USERNAME.equals(loginRequest.getUsername()) &&
            HARDCODED_PASSWORD.equals(loginRequest.getPassword())) {

            String token = jwtUtil.generateToken(loginRequest.getUsername());

            logger.info("User '{}' logged in successfully", loginRequest.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("username", loginRequest.getUsername());

            return ResponseEntity.ok(response);
        } else {
            logger.warn("Failed login attempt for user: {}", loginRequest.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}