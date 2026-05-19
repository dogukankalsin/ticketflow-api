package com.ticketflow.service;

import com.ticketflow.dto.TicketRequest;
import com.ticketflow.dto.TicketResponse;

import com.ticketflow.enums.TicketStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TicketService {

    TicketResponse createTicket(TicketRequest request);

    TicketResponse getTicketById(Long id);

    List<TicketResponse> getAllTickets();

    List<TicketResponse> getMyTickets();

    List<TicketResponse> getAssignedTickets();

    TicketResponse updateTicket(Long id, TicketRequest request);

    void deleteTicket(Long id);

    TicketResponse assignTicket(Long ticketId, Long assigneeId);

    TicketResponse updateStatus(Long ticketId, TicketStatus status);

    TicketResponse addWatcher(Long ticketId, Long watcherId);

    TicketResponse removeWatcher(Long ticketId, Long watcherId);
}
