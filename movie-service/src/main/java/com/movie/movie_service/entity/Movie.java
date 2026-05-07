package com.movie.movie_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String genre;

    private String language;

    private Integer duration;

    private Double rating;

    private LocalDate releaseDate;

    @Column(length = 2000)
    private String description;

    private String posterUrl;

    private Boolean active;

    private LocalDateTime createdAt;
}
