package com.movie.movie_service.service;


import com.movie.movie_service.dto.CreateMovieRequest;
import com.movie.movie_service.dto.MovieResponse;
import com.movie.movie_service.dto.UpdateMovieRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MovieService {
    MovieResponse createMovie(CreateMovieRequest request);

    List<MovieResponse> getAllMovies();

    MovieResponse getMovieById(Long id);

    MovieResponse updateMovie(Long id, UpdateMovieRequest request);

    void deleteMovie(Long id);

    List<MovieResponse> searchMovies(String keyword);

    List<MovieResponse> getActiveMovies();
}
