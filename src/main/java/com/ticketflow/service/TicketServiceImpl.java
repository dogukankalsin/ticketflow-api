package com.ticketflow.service;


import com.ticketflow.dto.TicketRequest;
import com.ticketflow.dto.TicketResponse;
import com.ticketflow.entity.Ticket;
import com.ticketflow.entity.User;
import com.ticketflow.enums.Role;
import com.ticketflow.enums.TicketPriority;
import com.ticketflow.enums.TicketStatus;
import com.ticketflow.mapper.TicketMapper;
import com.ticketflow.repository.TicketRepository;
import com.ticketflow.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final AuthFacade authFacade;
    private final UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, TicketMapper ticketMapper, AuthFacade authFacade, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.authFacade = authFacade;
        this.userRepository = userRepository;
    }

    @Override
    public TicketResponse createTicket(TicketRequest request) {

        User user = authFacade.getCurrentUser();

        Ticket ticket = new Ticket();
        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setPriority(
                request.priority() != null ? request.priority() : TicketPriority.MEDIUM
        );
        ticket.setCreatedBy(user);
        ticket.setStatus(TicketStatus.OPEN);
        Ticket saved = ticketRepository.save(ticket);
        return ticketMapper.toResponse(saved, user.getRole());
    }

    @Override
    public TicketResponse getTicketById(Long id) {
        User currUser = authFacade.getCurrentUser();
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
            if (!ticket.getCreatedBy().getId().equals(currUser.getId())&&currUser.getRole() == Role.USER) {
                throw new RuntimeException("Forbidden");
        }
        return ticketMapper.toResponse(ticket, currUser.getRole());
    }

    @PreAuthorize("hasAnyRole('SUPPORT','ADMIN')")
    @Override
    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets=ticketRepository.findAll();
        Role role= authFacade.getCurrentUser().getRole();
        return tickets.stream()
                .map(ticket -> ticketMapper.toResponse(ticket,role))
                .toList();
    }

    @Override
    public List<TicketResponse> getMyTickets() {
        User currUser = authFacade.getCurrentUser();
        Long userId = currUser.getId();
        List<Ticket> tickets = ticketRepository.findByCreatedById(userId);
        return tickets.stream()
                .map(ticket -> ticketMapper.toResponse(ticket, currUser.getRole()))
                .toList();

    }
    @PreAuthorize("hasRole('SUPPORT')")
    @Override
    public List<TicketResponse> getMyAssignedTickets() {
        User currUser=authFacade.getCurrentUser();
        List<Ticket> tickets= ticketRepository.findByAssignedTo_Id(currUser.getId());
        return tickets.stream()
                .map(ticket -> ticketMapper.toResponse(ticket, currUser.getRole()))
                .toList();
    }

    @PreAuthorize("hasAnyRole('SUPPORT','ADMIN')")
    @Override
    public List<TicketResponse> getAssignedTickets(Long userId, List<TicketStatus> statuses){
        List<TicketStatus> finalStatuses =
                (statuses == null)
                        ? List.of(TicketStatus.values())
                        : statuses;

        List<Ticket> tickets =
                ticketRepository.findByAssignedToIdAndStatusIn(userId, finalStatuses);
        Role role= authFacade.getCurrentUser().getRole();
        return tickets.stream()
                .map(ticket -> ticketMapper.toResponse(ticket, role))
                .toList();
    }

    @PreAuthorize("hasAnyRole('SUPPORT','ADMIN')")
    @Override
    public TicketResponse updateTicket(Long id, TicketRequest request) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        if (request.title() != null) {
            ticket.setTitle(request.title());
        }

        if (request.description() != null) {
            ticket.setDescription(request.description());
        }

        if (request.priority() != null) {
            ticket.setPriority(request.priority());
        }

        ticketRepository.save(ticket);

        return ticketMapper.toResponse(ticket, authFacade.getCurrentUser().getRole());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public void deleteTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticketRepository.delete(ticket);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TicketResponse assignTicket(Long ticketId, Long assigneeId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        User assignUser = userRepository.findById(assigneeId).orElseThrow(() -> new RuntimeException("User not found"));
        ticket.setAssignedTo(assignUser);
        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket, authFacade.getCurrentUser().getRole());
    }

    @PreAuthorize("hasAnyRole('SUPPORT','ADMIN')")
    @Override
    public TicketResponse updateStatus(Long ticketId, TicketStatus status) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setStatus(status);
        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket, authFacade.getCurrentUser().getRole());
    }

    @PreAuthorize("hasAnyRole('SUPPORT','ADMIN')")
    @Override
    public TicketResponse updatePriority(Long ticketId, TicketPriority priority) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setPriority(priority);
        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket, authFacade.getCurrentUser().getRole());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TicketResponse addWatcher(Long ticketId, Long watcherId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        User watcher = userRepository.findById(watcherId).orElseThrow(() -> new RuntimeException("User not found"));
        ticket.getWatchers().add(watcher);
        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket, authFacade.getCurrentUser().getRole());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public TicketResponse removeWatcher(Long ticketId, Long watcherId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
        User watcher = userRepository.findById(watcherId).orElseThrow(() -> new RuntimeException("User not found"));
        ticket.getWatchers().remove(watcher);
        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket, authFacade.getCurrentUser().getRole());
    }
}
