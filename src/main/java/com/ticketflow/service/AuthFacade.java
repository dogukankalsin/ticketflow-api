package com.ticketflow.service;

import com.ticketflow.entity.User;
import com.ticketflow.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthFacade {

    private final UserRepository userRepository;

    public AuthFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return (Long) principal;
    }

    public User getCurrentUser() {
        return userRepository.findById(getCurrentUserId())
                .orElseThrow();
    }
}
