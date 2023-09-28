package com.sourav.exception;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class MyErrorWebExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UnauthorisedException.class)
    public Mono<ServerResponse> handleIllegalState(ServerWebExchange exchange, UnauthorisedException exc) {
        exchange.getAttributes().putIfAbsent(ErrorAttributes.ERROR_ATTRIBUTE, exc);
        return ServerResponse.from(ErrorResponse.builder(exc, HttpStatus.FORBIDDEN, exc.getMessage()).build());
    }

}