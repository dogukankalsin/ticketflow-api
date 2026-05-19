package com.ticketflow.dto;

import com.ticketflow.enums.Role;

public record AuthResponse(String token, Role role) {
}
