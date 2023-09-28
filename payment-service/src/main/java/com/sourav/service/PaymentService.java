package com.sourav.service;

import com.sourav.entity.Payment;
import com.sourav.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(paymentValidation());
        payment.setTransactionId(UUID.randomUUID().toString());
        return repository.save(payment);
    }

    public String paymentValidation() {
        return new Random().nextBoolean() ? "success" : "false";
    }

    public List<Payment> getPaymentHistory(int orderId) {
        return repository.findByOrderId(orderId);
    }
}
