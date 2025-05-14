package com.moviespring.moviespring.Controllers.fe;

import com.moviespring.moviespring.model.Ticket;
import com.moviespring.moviespring.model.Movie;
import com.moviespring.moviespring.service.MovieService;
import com.moviespring.moviespring.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    // Giả lập số vé còn lại (30 vé ban đầu cho mỗi rạp)
    private static int remainingTicketsHaDong = 30;
    private static int remainingTicketsCauGiay = 30;

    // Lưu danh sách vé đã đặt (giả lập giỏ hàng và lịch sử)
    private static List<Ticket> cart = new ArrayList<>();
    private static List<Ticket> history = new ArrayList<>();

    // Getter tĩnh để truy cập cart và history từ feController
    public static List<Ticket> getCart() {
        return cart;
    }

    public static List<Ticket> getHistory() {
        return history;
    }

    @GetMapping("/{movieId}")
    public String bookingPage(@PathVariable Long movieId, Model model) {
        Optional<Movie> movieOptional = movieService.getMovieById(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            model.addAttribute("movie", movie);
            model.addAttribute("remainingTicketsHaDong", remainingTicketsHaDong);
            model.addAttribute("remainingTicketsCauGiay", remainingTicketsCauGiay);

            // Giả lập danh sách giờ chiếu dựa trên movieId
            List<String> showtimes = new ArrayList<>();
            if (movieId % 2 == 0) { // Phim có ID chẵn
                showtimes.add("2025-05-13T10:00");
                showtimes.add("2025-05-13T13:00");
                showtimes.add("2025-05-13T16:00");
                showtimes.add("2025-05-13T19:00");
                showtimes.add("2025-05-14T09:00");
            } else { // Phim có ID lẻ
                showtimes.add("2025-05-13T11:00");
                showtimes.add("2025-05-13T14:00");
                showtimes.add("2025-05-13T17:00");
                showtimes.add("2025-05-14T10:00");
                showtimes.add("2025-05-14T13:00");
            }
            model.addAttribute("showtimes", showtimes);

            return "fe/booking";
        } else {
            model.addAttribute("error", "Không tìm thấy phim với ID: " + movieId);
            return "fe/error";
        }
    }

    @PostMapping("/submit")
    public String submitBooking(@RequestParam Long movieId,
                                @RequestParam String userEmail,
                                @RequestParam int quantity,
                                @RequestParam String bookingDate,
                                @RequestParam String cinema,
                                Model model) {
        try {
            Optional<Movie> movieOptional = movieService.getMovieById(movieId);
            if (!movieOptional.isPresent()) {
                model.addAttribute("error", "Không tìm thấy phim với ID: " + movieId);
                return "fe/error";
            }
            Movie movie = movieOptional.get();

            // Chọn số vé còn lại dựa trên rạp
            int remainingTickets = "Hà Đông - 123 Đường Lê Lợi, Hà Đông, Hà Nội".equals(cinema) ? remainingTicketsHaDong : remainingTicketsCauGiay;

            // Kiểm tra số vé còn lại
            if (quantity > remainingTickets || quantity <= 0) {
                model.addAttribute("error", "Số vé không hợp lệ! Số vé còn lại tại " + cinema + ": " + remainingTickets);
                model.addAttribute("movie", movie);
                model.addAttribute("remainingTicketsHaDong", remainingTicketsHaDong);
                model.addAttribute("remainingTicketsCauGiay", remainingTicketsCauGiay);
                return "fe/booking";
            }

            // Parse bookingDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime bookingDateTime;
            try {
                bookingDateTime = LocalDateTime.parse(bookingDate, formatter);
            } catch (Exception e) {
                model.addAttribute("error", "Định dạng thời gian không hợp lệ: " + e.getMessage());
                model.addAttribute("movie", movie);
                model.addAttribute("remainingTicketsHaDong", remainingTicketsHaDong);
                model.addAttribute("remainingTicketsCauGiay", remainingTicketsCauGiay);
                return "fe/booking";
            }

            // Tạo và lưu ticket
            Ticket ticket = new Ticket();
            ticket.setMovieId(movieId);
            ticket.setUserEmail(userEmail);
            ticket.setQuantity(quantity);
            ticket.setBookingDateTime(bookingDateTime);
            ticket.setCinema(cinema);
            int totalPrice = quantity * 50000;
            ticket.setTotalPrice(totalPrice);

            ticket = ticketService.saveTicket(ticket);

            // Trừ số vé còn lại
            if ("Hà Đông - 123 Đường Lê Lợi, Hà Đông, Hà Nội".equals(cinema)) {
                remainingTicketsHaDong -= quantity;
            } else {
                remainingTicketsCauGiay -= quantity;
            }

            // Thêm vào giỏ hàng và lịch sử
            cart.add(ticket);
            history.add(ticket);

            // Điều hướng về index với thông báo thành công
            return "redirect:/index?success=true&totalPrice=" + totalPrice + "&remainingTicketsHaDong=" + remainingTicketsHaDong + "&remainingTicketsCauGiay=" + remainingTicketsCauGiay;
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi đặt vé: " + e.getMessage());
            Optional<Movie> movieOptional = movieService.getMovieById(movieId);
            model.addAttribute("movie", movieOptional.isPresent() ? movieOptional.get() : null);
            model.addAttribute("remainingTicketsHaDong", remainingTicketsHaDong);
            model.addAttribute("remainingTicketsCauGiay", remainingTicketsCauGiay);
            return "fe/booking";
        }
    }
}