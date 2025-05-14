package com.moviespring.moviespring.Controllers.fe;

import com.moviespring.moviespring.model.Movie;
import com.moviespring.moviespring.model.Ticket;
import com.moviespring.moviespring.service.MovieService;
import com.moviespring.moviespring.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class feController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping({"/", "/index", "/index.html"})
    public String index(@RequestParam(value = "success", required = false) Boolean success,
                        @RequestParam(value = "totalPrice", required = false) Integer totalPrice,
                        @RequestParam(value = "remainingTicketsHaDong", required = false) Integer remainingTicketsHaDong,
                        @RequestParam(value = "remainingTicketsCauGiay", required = false) Integer remainingTicketsCauGiay,
                        Model model) {
        List<Movie> allMovies = movieService.getAllMovies();

        // Trending Movies: Lấy 10 phim đầu tiên
        List<Movie> trendingMovies = allMovies.stream()
                .limit(10)
                .collect(Collectors.toList());

        // Popular Movies: Lấy 10 phim tiếp theo
        List<Movie> popularMovies = allMovies.stream()
                .skip(10)
                .limit(10)
                .collect(Collectors.toList());

        // Hiển thị thông báo nếu có
        if (success != null && success) {
            model.addAttribute("success", true);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("remainingTicketsHaDong", remainingTicketsHaDong);
            model.addAttribute("remainingTicketsCauGiay", remainingTicketsCauGiay);
        }

        model.addAttribute("trendingMovies", trendingMovies);
        model.addAttribute("popularMovies", popularMovies);
        return "fe/index";
    }

    @GetMapping("/movie/{id}")
    public String movieDetail(@PathVariable Long id, Model model) {
        return movieService.getMovieById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    return "fe/detail";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Không tìm thấy phim với ID: " + id);
                    return "fe/error";
                });
    }

    @GetMapping("/api/search")
    @ResponseBody
    public List<Movie> searchMovies(@RequestParam("query") String query) {
        return movieService.getAllMovies().stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", BookingController.getCart());
        return "fe/cart";
    }

    @GetMapping("/history")
    public String viewHistory(Model model) {
        model.addAttribute("history", BookingController.getHistory());
        return "fe/history";
    }
}