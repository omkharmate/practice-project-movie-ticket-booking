package com.movie.movie_service.controller;

import com.movie.movie_service.dto.*;
import com.movie.movie_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public MovieResponse createMovie(
            @RequestBody CreateMovieRequest request) {
        return movieService.createMovie(request);
    }

    @GetMapping
    public List<MovieResponse> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieResponse getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    public MovieResponse updateMovie(
            @PathVariable Long id,
            @RequestBody UpdateMovieRequest request) {
        return movieService.updateMovie(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/search")
    public List<MovieResponse> searchMovies(
            @RequestParam String keyword) {
        return movieService.searchMovies(keyword);
    }

    @GetMapping("/active")
    public List<MovieResponse> getActiveMovies() {
        return movieService.getActiveMovies();
    }
}