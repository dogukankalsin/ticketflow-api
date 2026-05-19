package com.ticketflow.service;


import com.ticketflow.dto.TicketRequest;
import com.ticketflow.dto.TicketResponse;
import com.ticketflow.entity.Ticket;
import com.ticketflow.entity.User;
import com.ticketflow.enums.TicketPriority;
import com.ticketflow.enums.TicketStatus;
import com.ticketflow.mapper.TicketMapper;
import com.ticketflow.repository.TicketRepository;
import com.ticketflow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    
    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, JwtService jwtService, UserRepository userRepository){
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @Override
    public TicketResponse createTicket(TicketRequest request) {
        Long userId = jwtService.getCurrentUserId();
        User user=userRepository.findById(userId).orElseThrow();
        
        Ticket ticket = new Ticket();
        ticket.setTitle(request.title());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setDescription(request.description());
        ticket.setPriority(
                request.priority() != null ? request.priority() : TicketPriority.MEDIUM
        );
        ticket.setCreatedBy(user);
        ticket.setCreatedAt(LocalDateTime.now());
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved,user.getRole());
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        return
    }

    @Override
    public List<TicketResponse> getAllTickets() {
        return List.of();
    }

    @Override
    public List<TicketResponse> getMyTickets() {
        Long userId = jwtService.getCurrentUserId();
        return List.of();
    }

    @Override
    public List<TicketResponse> getAssignedTickets() {
        Long userId = jwtService.getCurrentUserId();
        return List.of();
    }

    @Override
    public TicketResponse updateTicket(Long id, TicketRequest request) {
        Long userId = jwtService.getCurrentUserId();
        return null;
    }

    @Override
    public void deleteTicket(Long id) {
        Long userId = jwtService.getCurrentUserId();
    }

    @Override
    public TicketResponse assignTicket(Long ticketId, Long assigneeId) {
        Long userId = jwtService.getCurrentUserId();
        return null;
    }

    @Override
    public TicketResponse updateStatus(Long ticketId, TicketStatus status) {
        Long userId = jwtService.getCurrentUserId();
        return null;
    }

    @Override
    public TicketResponse addWatcher(Long ticketId, Long watcherId) {
        Long userId = jwtService.getCurrentUserId();
        return null;
    }

    @Override
    public TicketResponse removeWatcher(Long ticketId, Long watcherId) {
        Long userId = jwtService.getCurrentUserId();
        return null;
    }
}
