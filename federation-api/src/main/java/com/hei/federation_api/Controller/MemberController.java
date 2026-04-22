package com.hei.federation_api.Controller;

import com.hei.federation_api.Entity.CreateMember;
import com.hei.federation_api.Service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService service = new MemberService();

    @PostMapping
    public ResponseEntity<?> create(@RequestBody List<CreateMember> request) {
        try {
            return ResponseEntity.status(201).body(service.create(request));
        } catch (RuntimeException | SQLException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}