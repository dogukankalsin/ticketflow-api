package com.ticketflow.dto;

import com.ticketflow.enums.TicketPriority;
import com.ticketflow.enums.TicketStatus;

import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        String title,
        String description,
        TicketStatus status,
        TicketPriority priority,
        String createdBy,
        String assignedTo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}