package com.hei.federation_api.Controller;

import com.hei.federation_api.Service.FinancialAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collectivities")
public class FinancialAccountController {

    private final FinancialAccountService service;

    public FinancialAccountController(FinancialAccountService service) {
        this.service = service;
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam String at
    ) {
        try {
            return ResponseEntity.ok(service.getByCollectivityIdAt(id, at));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}