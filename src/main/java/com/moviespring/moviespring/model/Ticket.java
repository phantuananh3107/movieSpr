package com.moviespring.moviespring.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Column(name = "total_price")
    private double totalPrice;

    // Giả sử giá vé cố định là 10 USD/vé
    private static final double PRICE_PER_TICKET = 10.0;

    // Constructors
    public Ticket() {
    }

    public Ticket(Movie movie, String userEmail, int quantity, LocalDateTime bookingDate) {
        this.movie = movie;
        this.userEmail = userEmail;
        this.quantity = quantity;
        this.bookingDate = bookingDate;
        this.totalPrice = quantity * PRICE_PER_TICKET;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}