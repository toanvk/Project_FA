package com.fpt.controller;

import com.fpt.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check-bank")
public class CheckBankController {
    private final OrderService orderService;

    public CheckBankController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> checkBank() {
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
