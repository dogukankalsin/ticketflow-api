package com.ticketflow.dto;

import com.ticketflow.enums.TicketPriority;
import jakarta.validation.constraints.NotBlank;

public record TicketRequest(@NotBlank String title, @NotBlank String description, TicketPriority priority ) {
}
