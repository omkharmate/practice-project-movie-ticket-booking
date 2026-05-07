package com.movie.movie_service.repository;

import com.movie.movie_service.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository <Movie,Long>{

    List<Movie> findByActiveTrue();

    List<Movie> findByTitleContainingIgnoreCase(String keyword);

    List<Movie> findByGenre(String genre);

    List<Movie> findByLanguage(String language);
}
