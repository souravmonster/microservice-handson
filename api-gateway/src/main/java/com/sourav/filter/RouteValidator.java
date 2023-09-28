package com.sourav.filter;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openAPIEndpoints = List.of(
            "/auth/register",
            "/auth/token",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> openAPIEndpoints.stream()
                    .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
