package com.ticketflow.service;

import com.ticketflow.entity.User;
import com.ticketflow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User save(User user);
    List<User> findAll();
    User findById(Long id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existByUsername(String username);
}
