package com.sourav;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/orderFallback")
    public Mono<String> orderServiceFallback() {
        return Mono.just("Order Service is taking too long to respond or is down, please try after sometime!!");
    }

    @RequestMapping("/paymentFallback")
    public Mono<String> paymentServiceFallback() {
        return Mono.just("Payment Service is taking too long to respond or is down, please try after sometime!!");
    }
}
