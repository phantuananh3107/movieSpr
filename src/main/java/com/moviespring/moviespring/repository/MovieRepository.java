package com.moviespring.moviespring.repository;

import com.moviespring.moviespring.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}