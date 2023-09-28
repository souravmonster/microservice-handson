package com.sourav.controller;

import com.sourav.dto.AuthRequest;
import com.sourav.dto.AuthResponse;
import com.sourav.entity.User;
import com.sourav.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        return authService.saveUser(user);
    }

    @PostMapping("/token")
    public AuthResponse getToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        return authService.generateToken(authRequest.getUsername());
    }

    @GetMapping("/validate")
    public String getToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "Token is valid";
    }
}
