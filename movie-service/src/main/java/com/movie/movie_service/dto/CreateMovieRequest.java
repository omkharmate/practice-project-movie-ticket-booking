package com.movie.movie_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMovieRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotBlank(message = "Language is required")
    private String language;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Rating is required")
    @DecimalMin(value = "0.0", message = "Rating must be >= 0")
    @DecimalMax(value = "10.0", message = "Rating must be <= 10")
    private Double rating;

    @NotNull(message = "Release date is required")
    private LocalDate releaseDate;

    @Size(max = 500, message = "Description must be under 500 characters")
    private String description;

    private String posterUrl;
}