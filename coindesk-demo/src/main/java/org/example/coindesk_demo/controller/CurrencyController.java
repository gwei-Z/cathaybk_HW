package org.example.coindesk_demo.controller;

import org.example.coindesk_demo.entity.Currency;
import org.example.coindesk_demo.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    private final CurrencyService svc;

    public CurrencyController(CurrencyService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Currency> all() {
        return svc.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Currency> one(@PathVariable String code) {
        return svc.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Currency create(@RequestBody Currency c) {
        return svc.save(c);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Currency> update(@PathVariable String code,
                                           @RequestBody Currency c) {
        if (!svc.findByCode(code).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        c.setCode(code);
        return ResponseEntity.ok(svc.save(c));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        if (!svc.findByCode(code).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        svc.deleteByCode(code);
        return ResponseEntity.noContent().build();
    }
}
