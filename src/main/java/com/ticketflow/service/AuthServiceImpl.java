package com.ticketflow.service;

import com.ticketflow.dto.AuthResponse;
import com.ticketflow.dto.LoginRequest;
import com.ticketflow.dto.MessageResponse;
import com.ticketflow.dto.RegisterRequest;
import com.ticketflow.entity.User;
import com.ticketflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRep;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRep, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRep = userRep;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public MessageResponse register(RegisterRequest request) {
        if (userRep.existsByEmail(request.email())) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRep.save(user);
        return new MessageResponse("Registration successful");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRep.findByEmail(request.email()).orElseThrow();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return new AuthResponse(jwtService.generateToken(user), user.getRole());
    }
}
