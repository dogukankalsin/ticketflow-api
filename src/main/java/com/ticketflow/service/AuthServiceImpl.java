package com.ticketflow.service;

import com.ticketflow.dto.AuthResponse;
import com.ticketflow.dto.LoginRequest;
import com.ticketflow.dto.MessageResponse;
import com.ticketflow.dto.RegisterRequest;
import com.ticketflow.entity.User;
import com.ticketflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthServiceImpl implements AuthService {
    UserRepository userRep;
    private final PasswordEncoder passwordEncoder;
    JwtService jwtService;

    public AuthServiceImpl(UserRepository userRep, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRep = userRep;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public MessageResponse register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        userRep.save(user);
        return new MessageResponse("Registration successful");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRep.findByEmail(request.email()).orElseThrow();
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Şifre veya email yanlış");
        }
        return new AuthResponse(jwtService.generateToken(user), user.getRole());
    }
}
