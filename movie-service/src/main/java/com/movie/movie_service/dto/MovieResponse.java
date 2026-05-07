package com.movie.movie_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MovieResponse {

    private Long id;

    private String title;

    private String genre;

    private String language;

    private Integer duration;

    private Double rating;

    private LocalDate releaseDate;

    private String description;

    private String posterUrl;

    private Boolean active;
}