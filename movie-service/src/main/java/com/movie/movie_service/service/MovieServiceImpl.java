package com.movie.movie_service.service;

import com.movie.movie_service.dto.CreateMovieRequest;
import com.movie.movie_service.dto.MovieResponse;
import com.movie.movie_service.dto.UpdateMovieRequest;
import com.movie.movie_service.entity.Movie;
import com.movie.movie_service.exception.ResourceNotFoundException;
import com.movie.movie_service.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public MovieResponse createMovie(CreateMovieRequest request) {

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .duration(request.getDuration())
                .rating(request.getRating())
                .releaseDate(request.getReleaseDate())
                .description(request.getDescription())
                .posterUrl(request.getPosterUrl())
                .active(true)
                .createdAt(LocalDateTime.now())
                .build();

        Movie savedMovie = movieRepository.save(movie);

        return mapToResponse(savedMovie);
    }

    @Override
    public List<MovieResponse> getAllMovies() {

        List<Movie> movies = movieRepository.findAll();

        return movies.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Cacheable(value = "movies", key = "#id")
    public MovieResponse getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    @Override
    @CachePut(value = "movies", key = "#id")
    public MovieResponse updateMovie(Long id,
                                     UpdateMovieRequest request) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Movie not found with id: " + id));

        movie.setTitle(request.getTitle());
        movie.setGenre(request.getGenre());
        movie.setLanguage(request.getLanguage());
        movie.setDuration(request.getDuration());
        movie.setRating(request.getRating());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setDescription(request.getDescription());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setActive(request.getActive());

        Movie updatedMovie = movieRepository.save(movie);

        return mapToResponse(updatedMovie);
    }

    @Override
    @CacheEvict(value = "movies", key = "#id")
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Movie not found with id: " + id));

        movie.setActive(false);

        movieRepository.save(movie);
    }

    @Override
    public List<MovieResponse> searchMovies(String keyword) {

        List<Movie> movies =
                movieRepository.findByTitleContainingIgnoreCase(keyword);

        return movies.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<MovieResponse> getActiveMovies() {

        List<Movie> movies = movieRepository.findByActiveTrue();

        return movies.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private MovieResponse mapToResponse(Movie movie) {

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .duration(movie.getDuration())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .description(movie.getDescription())
                .posterUrl(movie.getPosterUrl())
                .active(movie.getActive())
                .build();
    }
}
