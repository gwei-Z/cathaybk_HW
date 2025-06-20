package org.example.coindesk_demo.controller;

import org.example.coindesk_demo.dto.CoindeskRawResponse;
import org.example.coindesk_demo.dto.CoindeskTransformedResponse;
import org.example.coindesk_demo.service.CoindeskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coindesk")
public class CoindeskController {
    private final CoindeskService svc;

    public CoindeskController(CoindeskService svc) {
        this.svc = svc;
    }

    @GetMapping("/raw")
    public CoindeskRawResponse raw() {
        return svc.fetchRaw();
    }

    @GetMapping
    public CoindeskTransformedResponse transformed() {
        return svc.transform();
    }
}
