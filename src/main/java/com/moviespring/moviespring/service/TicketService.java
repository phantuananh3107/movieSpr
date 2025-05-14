package com.moviespring.moviespring.service;

import com.moviespring.moviespring.model.Ticket;
import com.moviespring.moviespring.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public int getTotalTicketsSold() {
        return ticketRepository.findAll().stream()
                .mapToInt(Ticket::getQuantity) // Không cần kiểm tra null vì quantity là int
                .sum();
    }

    public double getTotalRevenue() {
        return ticketRepository.findAll().stream()
                .mapToDouble(Ticket::getTotalPrice) // Không cần kiểm tra null vì totalPrice là int
                .sum();
    }
}