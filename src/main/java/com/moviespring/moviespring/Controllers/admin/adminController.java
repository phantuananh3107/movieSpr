package com.moviespring.moviespring.Controllers.admin;

import com.moviespring.moviespring.model.Movie;
import com.moviespring.moviespring.service.MovieService;
import com.moviespring.moviespring.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private TicketService ticketService;

    @GetMapping({"", "/movies"})
    public String admin(Model model, @RequestParam(required = false) String sort) {
        List<MovieService.MovieWithStats> moviesWithStats = movieService.getAllMoviesWithStats(sort);
        model.addAttribute("listMovies", moviesWithStats);

        int totalPurchases = ticketService.getTotalTicketsSold(); // Tổng lượt mua
        double totalRevenue = ticketService.getTotalRevenue();    // Tổng doanh thu
        long totalTicketsSold = ticketService.getTotalTicketsSold(); // Tổng vé bán (giả sử TicketService có phương thức này)
        model.addAttribute("totalPurchases", totalPurchases);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalTicketsSold", totalTicketsSold);

        List<MovieService.MovieWithStats> topMoviesWithStats = movieService.getTopMoviesByTicketCount(5);
        model.addAttribute("topMovies", topMoviesWithStats);

        return "admin/movies";
    }

    @GetMapping("/movies/new")
    public String newMovie(Model model) {
        model.addAttribute("movie", new Movie());
        return "admin/newMovie";
    }

    @GetMapping("/movies/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id).orElseThrow(() -> new IllegalArgumentException("Invalid movie ID: " + id));
        model.addAttribute("movie", movie);
        return "admin/editMovie";
    }

    @PostMapping("/movies/save")
    public String saveMovie(@ModelAttribute("movie") Movie movie) {
        movieService.saveMovie(movie);
        return "redirect:/admin/movies";
    }

    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/admin/movies";
    }
}