package com.hei.federation_api.Controller;

import com.hei.federation_api.Service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Note: URL est /collectivites (sans i) comme dans la spec OAS
@RestController
@RequestMapping("/collectivites")
public class StatisticController {

    private final StatisticService service;

    public StatisticController(StatisticService service) {
        this.service = service;
    }

    // GET /collectivites/{id}/statistics
    @GetMapping("/{id}/statistics")
    public ResponseEntity<?> getLocalStatistics(
            @PathVariable String id,
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            return ResponseEntity.ok(service.getLocalStatistics(id, from, to));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /collectivites/statistics
    @GetMapping("/statistics")
    public ResponseEntity<?> getOverallStatistics(
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            return ResponseEntity.ok(service.getOverallStatistics(from, to));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}