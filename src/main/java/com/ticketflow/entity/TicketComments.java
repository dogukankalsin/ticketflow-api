package com.ticketflow.entity;

import jakarta.persistence.*;
import lombok.*;



import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter

public class TicketComments {
    @Id
    private Long id;

    @Column(length = 3000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
