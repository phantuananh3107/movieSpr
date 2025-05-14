package com.moviespring.moviespring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moviespring.moviespring.model.Ticket; // Đảm bảo import đúng model Ticket

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t.movieId, SUM(t.quantity) as totalTickets, COUNT(t) as purchaseCount FROM Ticket t WHERE t.movieId = ?1 GROUP BY t.movieId")
    Object[] getTicketAndPurchaseCountByMovieId(Long movieId);

    @Query("SELECT t.movieId, SUM(t.quantity) as totalTickets, COUNT(t) as purchaseCount FROM Ticket t GROUP BY t.movieId ORDER BY totalTickets DESC")
    List<Object[]> findTopMoviesByTicketCount();

    @Query("SELECT t.movieId, SUM(t.quantity) as totalTickets, COUNT(t) as purchaseCount FROM Ticket t GROUP BY t.movieId")
    List<Object[]> getAllMovieTicketAndPurchaseCounts();
}