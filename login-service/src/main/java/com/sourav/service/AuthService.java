package com.sourav.service;

import com.sourav.dto.AuthResponse;
import com.sourav.entity.User;
import com.sourav.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;
    public ResponseEntity<String> saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        if (!Optional.of(savedUser).isEmpty()) {
            return new ResponseEntity<>("User added to the system", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed!! Invalid user", HttpStatus.BAD_REQUEST);
        }
    }

    public AuthResponse generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
