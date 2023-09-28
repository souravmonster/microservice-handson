package com.sourav.filter;

import com.sourav.exception.UnauthorisedException;
import com.sourav.util.JWTUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JWTUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new UnauthorisedException(HttpStatus.FORBIDDEN, "Not Authorised from Gateway");
                }
                String authorizationHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    authorizationHeader = authorizationHeader.substring(7);
                }
                try {
                    jwtUtil.validateToken(authorizationHeader);
                } catch (Exception e) {
                    throw new UnauthorisedException(HttpStatus.FORBIDDEN, "Not Authorised from Gateway");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
