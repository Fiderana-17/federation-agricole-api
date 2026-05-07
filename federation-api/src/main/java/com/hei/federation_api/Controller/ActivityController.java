package com.hei.federation_api.Controller;

import com.hei.federation_api.Entity.CreateActivityMemberAttendance;
import com.hei.federation_api.Entity.CreateCollectivityActivity;
import com.hei.federation_api.Service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class ActivityController {

    private final ActivityService service;

    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<?> create(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivity> request
    ) {
        try {
            return ResponseEntity.ok(service.create(id, request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<?> getAll(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getAll(id));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> createAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<CreateActivityMemberAttendance> request
    ) {
        try {
            return ResponseEntity.status(201).body(service.createAttendance(id, activityId, request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> getAttendance(
            @PathVariable String id,
            @PathVariable String activityId
    ) {
        try {
            return ResponseEntity.ok(service.getAttendance(id, activityId));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}