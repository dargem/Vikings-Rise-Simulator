package com.vikingsrise.simulator.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RootController {

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> apiInfo() {
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
    @ResponseBody
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    // Fallback for React Router - serve index.html for all non-API routes
    @RequestMapping(value = {"/"})
    public String index() {
        return "forward:/index.html";
    }
}
