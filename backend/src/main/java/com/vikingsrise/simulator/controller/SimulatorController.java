package com.vikingsrise.simulator.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikingsrise.simulator.dto.SimulationRequestDTO;
import com.vikingsrise.simulator.service.SimulatorService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class SimulatorController {

    @Autowired
    private SimulatorService simulatorService;

    @PostMapping("/simulate")
    public ResponseEntity<?> runSimulation(@RequestBody SimulationRequestDTO request) {
        try {
            Object result = simulatorService.runSimulation(request.getConfig(), request.getMarches());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/commanders")
    public ResponseEntity<List<Map<String, String>>> getCommanders() {
        try {
            List<Map<String, String>> commanders = simulatorService.getCommanders();
            return ResponseEntity.ok(commanders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/skills")
    public ResponseEntity<List<Map<String, String>>> getSkills() {
        try {
            List<Map<String, String>> skills = simulatorService.getSkills();
            return ResponseEntity.ok(skills);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
