package com.movie.movie_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMovieRequest {

    private String title;

    private String genre;

    private String language;

    private Integer duration;

    private Double rating;

    private LocalDate releaseDate;

    private String description;

    private String posterUrl;
}