package com.ticketflow.dto;

import com.ticketflow.enums.TicketPriority;

public record TicketRequest(String title, String description, TicketPriority priority ) {
}
