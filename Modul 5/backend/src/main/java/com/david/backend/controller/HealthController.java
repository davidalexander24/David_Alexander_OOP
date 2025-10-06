package com.david.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
    @GetMapping("/health")
    public Map<String, Object> healthCheck() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("message", "Jetpack Joyride Backend is running!");
        status.put("timestamp", System.currentTimeMillis());
        return status;
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "CS6_David_Backend");
        response.put("version", "1.0");
        response.put("description", "Backend for jetpack joyride game");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("players", "/api/players");
        endpoints.put("scores", "/api/scores");
        endpoints.put("leaderboard", "/api/scores/leaderboard");
        endpoints.put("health", "/api/health");

        response.put("endpoints", endpoints);
        return response;
    }
}
