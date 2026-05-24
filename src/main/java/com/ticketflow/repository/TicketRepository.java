package com.ticketflow.repository;

import com.ticketflow.entity.Ticket;
import com.ticketflow.entity.User;
import com.ticketflow.enums.TicketPriority;
import com.ticketflow.enums.TicketStatus;
import org.hibernate.query.criteria.JpaDerivedRoot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCreatedById(Long userId);

    List<Ticket> findByAssignedToIdAndStatusIn(
            Long userId,
            List<TicketStatus> statuses
    );
    List<Ticket> findByAssignedTo_Id(Long id);

    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByPriority(TicketPriority priority);

    List<Ticket> findByWatchers_Id(Long userId);

    List<Ticket> findByStatusNot(TicketStatus status);


}
