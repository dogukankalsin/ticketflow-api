package com.ticketflow.mapper;

import com.ticketflow.dto.TicketResponse;
import com.ticketflow.entity.Ticket;
import com.ticketflow.enums.Role;
import com.ticketflow.enums.TicketPriority;
import com.ticketflow.enums.TicketStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketMapper {

    public TicketResponse toResponse(Ticket ticket, Role role) {

        Long id = ticket.getId();
        String title = ticket.getTitle();
        String description = ticket.getDescription();
        TicketStatus status = ticket.getStatus();
        LocalDateTime createdAt = ticket.getCreatedAt();

        TicketPriority priority = null;
        String createdBy = null;
        String assignedTo = null;

        if (role == Role.ADMIN) {
            priority = ticket.getPriority();
            createdBy = ticket.getCreatedBy().getUsername();
            assignedTo = ticket.getAssignedTo() != null ? ticket.getAssignedTo().getUsername() : null;
        }
        if (role == Role.SUPPORT) {
            priority = ticket.getPriority();
            assignedTo = ticket.getAssignedTo() != null ? ticket.getAssignedTo().getUsername() : null;
        }
        return new TicketResponse(
                id, title, description, status, priority, createdBy, assignedTo, createdAt
        );

    }
}