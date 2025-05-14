package com.moviespring.moviespring.service;

import com.moviespring.moviespring.model.Movie;
import com.moviespring.moviespring.repository.MovieRepository;
import com.moviespring.moviespring.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public MovieStats getMovieStatsById(Long movieId) {
        Object[] result = ticketRepository.getTicketAndPurchaseCountByMovieId(movieId);
        if (result != null && result.length == 3) {
            return new MovieStats((Long) result[0], (Long) result[1], (Long) result[2]);
        }
        return new MovieStats(movieId, 0L, 0L);
    }

    public List<MovieWithStats> getTopMoviesByTicketCount(int limit) {
        List<Object[]> ticketStats = ticketRepository.findTopMoviesByTicketCount();
        return ticketStats.stream()
                .limit(limit)
                .map(result -> {
                    Long movieId = (Long) result[0];
                    Long totalTickets = (Long) result[1];
                    Long purchaseCount = (Long) result[2];
                    Movie movie = movieRepository.findById(movieId).orElse(null);
                    return movie != null ? new MovieWithStats(movie, totalTickets, purchaseCount) : null;
                })
                .filter(stats -> stats != null)
                .collect(Collectors.toList());
    }

    public List<MovieWithStats> getAllMoviesWithStats(String sort) {
        // Lấy tất cả phim
        List<Movie> allMovies = movieRepository.findAll();
        // Lấy thống kê vé từ ticketRepository
        List<Object[]> ticketStats = ticketRepository.getAllMovieTicketAndPurchaseCounts();
        // Tạo map để ánh xạ movieId với stats
        var statsMap = ticketStats.stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> new MovieStats((Long) result[0], (Long) result[1], (Long) result[2])
                ));

        // Ghép tất cả phim với stats (nếu có)
        List<MovieWithStats> movieStatsList = allMovies.stream().map(movie -> {
            MovieStats stats = statsMap.getOrDefault(movie.getId(), new MovieStats(movie.getId(), 0L, 0L));
            return new MovieWithStats(movie, stats.getTotalTickets(), stats.getPurchaseCount());
        }).collect(Collectors.toList());

        // Sắp xếp
        if ("票".equals(sort)) {
            movieStatsList.sort(Comparator.comparingLong(MovieWithStats::getTotalTickets).reversed());
        } else {
            movieStatsList.sort(Comparator.comparingLong(m -> m.getMovie().getId()));
        }
        return movieStatsList;
    }

    public static class MovieWithStats {
        private final Movie movie;
        private final long totalTickets;
        private final long purchaseCount;

        public MovieWithStats(Movie movie, long totalTickets, long purchaseCount) {
            this.movie = movie;
            this.totalTickets = totalTickets;
            this.purchaseCount = purchaseCount;
        }

        public Movie getMovie() {
            return movie;
        }

        public long getTotalTickets() {
            return totalTickets;
        }

        public long getPurchaseCount() {
            return purchaseCount;
        }
    }

    public static class MovieStats {
        private final Long movieId;
        private final Long totalTickets;
        private final Long purchaseCount;

        public MovieStats(Long movieId, Long totalTickets, Long purchaseCount) {
            this.movieId = movieId;
            this.totalTickets = totalTickets;
            this.purchaseCount = purchaseCount;
        }

        public Long getTotalTickets() {
            return totalTickets;
        }

        public Long getPurchaseCount() {
            return purchaseCount;
        }
    }
}