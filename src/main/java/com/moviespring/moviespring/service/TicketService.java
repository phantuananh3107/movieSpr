package com.moviespring.moviespring.service;

import com.moviespring.moviespring.model.Movie;
import com.moviespring.moviespring.model.Ticket;
import com.moviespring.moviespring.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket bookTicket(Movie movie, String userEmail, int quantity) {
        Ticket ticket = new Ticket(movie, userEmail, quantity, LocalDateTime.now());
        return ticketRepository.save(ticket);
    }
}