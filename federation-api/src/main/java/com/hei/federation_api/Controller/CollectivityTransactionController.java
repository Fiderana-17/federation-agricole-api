package com.hei.federation_api.Controller;

import com.hei.federation_api.Service.CollectivityTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collectivities")
public class CollectivityTransactionController {

    private final CollectivityTransactionService service;

    public CollectivityTransactionController(CollectivityTransactionService service) {
        this.service = service;
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable String id,
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            return ResponseEntity.ok(service.getByPeriod(id, from, to));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}