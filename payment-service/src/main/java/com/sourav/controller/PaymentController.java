package com.sourav.controller;

import com.sourav.entity.Payment;
import com.sourav.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment) {
        return service.doPayment(payment);
    }

    @GetMapping("{orderId}")
    public List<Payment> getPaymentHistory(@PathVariable int orderId) {
        return service.getPaymentHistory(orderId);
    }
}
