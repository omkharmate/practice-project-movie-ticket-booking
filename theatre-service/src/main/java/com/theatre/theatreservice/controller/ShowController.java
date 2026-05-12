package com.theatre.theatreservice.controller;

import com.theatre.theatreservice.dto.ShowRequest;
import com.theatre.theatreservice.dto.ShowResponse;
import com.theatre.theatreservice.service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(
            @Valid @RequestBody ShowRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)   // 201, not 200
                .body(showService.createShow(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowResponse> getShowById(@PathVariable Long id) {

        return ResponseEntity.ok(showService.getShowById(id));
    }

    // GET /shows/movie/42
    // "Give me all shows for movie 42"
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowResponse>> getShowsByMovie(
            @PathVariable Long movieId) {

        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    // GET /shows/movie/42?date=2026-05-15
    // "Give me all shows for movie 42 on May 15"
    // This is the main customer-facing API
    @GetMapping("/movie/{movieId}/date")
    public ResponseEntity<List<ShowResponse>> getShowsByMovieAndDate(
            @PathVariable Long movieId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(showService.getShowsByMovieAndDate(movieId, date));
    }

    // PATCH — partial update, only changing status
    // We use PATCH not PUT because we are only changing one field
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ShowResponse> cancelShow(@PathVariable Long id) {

        return ResponseEntity.ok(showService.cancelShow(id));
    }
}