package com.vikingsrise.simulator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        return ResponseEntity.ok(Map.of(
            "name", "Vikings Rise Simulator API",
            "version", "1.0.0",
            "status", "running",
            "endpoints", Map.of(
                "skills", "/api/skills",
                "commanders", "/api/commanders", 
                "simulate", "/api/simulate (POST)"
            )
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
