package com.sourav.service;

import com.sourav.dto.Payment;
import com.sourav.dto.TransactionRequest;
import com.sourav.dto.TransactionResponse;
import com.sourav.entity.Order;
import com.sourav.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private RestTemplate template;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;

    public TransactionResponse saveOrder(TransactionRequest request) {
        Order order = request.getOrder();
        String result = "";
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setOrderAmount(order.getPrice());
        Payment response = template.postForObject(ENDPOINT_URL, payment, Payment.class);
        result = response.getPaymentStatus().equals("success") ? "payment processing successful and order placed" : "there is a failure in payment api , order added to cart";
        repository.save(order);
        return new TransactionResponse(order, response.getOrderAmount(), response.getTransactionId(), result);
    }
}
