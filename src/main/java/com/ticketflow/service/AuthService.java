package com.ticketflow.service;

import com.ticketflow.dto.AuthResponse;
import com.ticketflow.dto.LoginRequest;
import com.ticketflow.dto.MessageResponse;
import com.ticketflow.dto.RegisterRequest;

public interface AuthService {
    MessageResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
