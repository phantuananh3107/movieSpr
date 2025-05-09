package com.moviespring.moviespring.Controllers.fe;

import com.moviespring.moviespring.model.Movie;
import com.moviespring.moviespring.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class lobbyController {

    @Autowired
    private MovieService movieService;

    @RequestMapping("/lobby")
    public String home(Model model) {
        List<Movie> allMovies = movieService.getAllMovies();

        // Home Movies: Lấy phim có vote_average cao nhất (Top 10)
        List<Movie> homeMovies = allMovies.stream()
            .filter(movie -> movie.getVoteAverage() > 0)
            .sorted((m1, m2) -> Double.compare(m2.getVoteAverage(), m1.getVoteAverage()))
            .limit(10)
            .collect(Collectors.toList());

        model.addAttribute("homeMovies", homeMovies);
        return "fe/home";
    }
}