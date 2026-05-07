package com.hei.federation_api.Controller;

import com.hei.federation_api.Entity.CreateMemberPayment;
import com.hei.federation_api.Service.MemberPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberPaymentController {

    private final MemberPaymentService service;

    public MemberPaymentController(MemberPaymentService service) {
        this.service = service;
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<?> create(
            @PathVariable String id,
            @RequestBody List<CreateMemberPayment> request
    ) {
        try {
            return ResponseEntity.status(201).body(service.create(id, request));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}