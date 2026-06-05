package com.movie.movie_service.service;

import com.movie.movie_service.dto.CreateMovieRequest;
import com.movie.movie_service.dto.MovieResponse;
import com.movie.movie_service.dto.UpdateMovieRequest;
import com.movie.movie_service.entity.Movie;
import com.movie.movie_service.exception.ResourceNotFoundException;
import com.movie.movie_service.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    // saves new movie, always active on creation
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

        return mapToResponse(movieRepository.save(movie));
    }

    // paginated to avoid OOM — cached per page+size combination
    @Override
    @Cacheable(value = "allMovies", key = "#page + '-' + #size")
    public Page<MovieResponse> getAllMovies(int page, int size) {
        return movieRepository.findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    // cached by id — cache miss triggers DB fetch
    @Override
    @Cacheable(value = "movies", key = "#id")
    public MovieResponse getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + id));
    }

    // single UPDATE query — no SELECT at all, createdAt is never touched
    @Override
    @Transactional
    @CachePut(value = "movies", key = "#id")
    @CacheEvict(value = {"allMovies", "activeMovies", "movieSearch"}, allEntries = true)
    public MovieResponse updateMovie(Long id, UpdateMovieRequest request) {
        int updated = movieRepository.updateMovieById(
                id,
                request.getTitle(),
                request.getGenre(),
                request.getLanguage(),
                request.getDuration(),
                request.getRating(),
                request.getReleaseDate(),
                request.getDescription(),
                request.getPosterUrl(),
                request.getActive()
        );
        if (updated == 0) {
            throw new ResourceNotFoundException("Movie not found with id: " + id);
        }
        return MovieResponse.builder()
                .id(id)
                .title(request.getTitle())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .duration(request.getDuration())
                .rating(request.getRating())
                .releaseDate(request.getReleaseDate())
                .description(request.getDescription())
                .posterUrl(request.getPosterUrl())
                .active(request.getActive())
                .build();
    }

    // single UPDATE query — sets active=false without loading the entity first
    @Override
    @Transactional
    @CacheEvict(value = {"movies", "allMovies", "activeMovies", "movieSearch"}, allEntries = true)
    public void deleteMovie(Long id) {
        int updated = movieRepository.softDeleteById(id);
        if (updated == 0) {
            throw new ResourceNotFoundException("Movie not found with id: " + id);
        }
    }

    // uses FULLTEXT search for >= 3 chars, falls back to LIKE for shorter keywords
    // cache key normalized to lowercase+trim to avoid duplicate cache entries
    @Override
    @Cacheable(value = "movieSearch", key = "#keyword?.toLowerCase().trim() ?: 'empty'")
    public List<MovieResponse> searchMovies(String keyword) {
        if (keyword == null || keyword.isBlank()) {         // null/blank guard
            return List.of();
        }
        if (keyword.trim().length() < 3) {                  // LIKE fallback for short keywords
            return movieRepository.findByTitleContainingIgnoreCase(keyword.trim())
                    .stream().map(this::mapToResponse).toList();
        }
        return movieRepository.searchByTitle(keyword.trim()) // FULLTEXT for longer keywords
                .stream().map(this::mapToResponse).toList();
    }

    // cached — only invalidated when a movie is updated or deleted
    @Override
    @Cacheable(value = "activeMovies")
    public List<MovieResponse> getActiveMovies() {
        return movieRepository.findByActiveTrue()
                .stream()
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