package com.hei.federation_api.Controller;

import com.hei.federation_api.Entity.CreateMember;
import com.hei.federation_api.Service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<CreateMember> request) {
        try {
            return ResponseEntity.status(201).body(service.create(request));
        } catch (RuntimeException e) { // ❗ SQLException supprimé
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}