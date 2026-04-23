package com.hei.federation_api.Controller;

import com.hei.federation_api.Entity.AssignIdentityRequest;
import com.hei.federation_api.Entity.CreateCollectivity;
import com.hei.federation_api.Service.CollectivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService service;

    public CollectivityController(CollectivityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<CreateCollectivity> request) {
        try {
            return ResponseEntity.status(201).body(service.create(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/identity")
    public ResponseEntity<?> assignIdentity(
            @PathVariable String id,
            @RequestBody AssignIdentityRequest request
    ) {
        try {
            service.assignIdentity(id, request.name, request.number);
            return ResponseEntity.ok("Assigned successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}