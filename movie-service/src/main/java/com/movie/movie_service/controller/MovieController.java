package com.movie.movie_service.controller;

import com.movie.movie_service.dto.*;
import com.movie.movie_service.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(
            @Valid @RequestBody CreateMovieRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(movieService.createMovie(request));
    }

    @GetMapping
    public ResponseEntity<Page<MovieResponse>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(movieService.getAllMovies(page, size)); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));        // 200, 404 handled by GlobalExceptionHandler
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMovieRequest request) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();                       // 204 - deleted, no body
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> searchMovies(
            @RequestParam String keyword) {
        return ResponseEntity.ok(movieService.searchMovies(keyword));   // 200
    }

    @GetMapping("/active")
    public ResponseEntity<List<MovieResponse>> getActiveMovies() {
        return ResponseEntity.ok(movieService.getActiveMovies());       // 200
    }
}