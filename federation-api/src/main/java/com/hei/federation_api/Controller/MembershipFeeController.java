package com.hei.federation_api.Controller;

import com.hei.federation_api.Entity.CreateMembershipFee;
import com.hei.federation_api.Service.MembershipFeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class MembershipFeeController {

    private final MembershipFeeService service;

    public MembershipFeeController(MembershipFeeService service) {
        this.service = service;
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<?> getAll(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getByCollectivityId(id));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/membershipFees")
    public ResponseEntity<?> create(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> request
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
}