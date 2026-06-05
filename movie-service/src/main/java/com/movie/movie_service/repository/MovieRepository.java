package com.movie.movie_service.repository;

import com.movie.movie_service.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    // fetches only active movies for listing
    List<Movie> findByActiveTrue();

    // fallback for short keywords (< 3 chars) — LIKE '%keyword%', no index used
    List<Movie> findByTitleContainingIgnoreCase(String keyword);

    // fulltext search for keywords >= 3 chars — uses MySQL FULLTEXT index, faster than LIKE
    @Query(value = "SELECT * FROM movies WHERE MATCH(title) AGAINST (:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    List<Movie> searchByTitle(@Param("keyword") String keyword);

    // single UPDATE — sets active=false without loading the entity first
    @Modifying
    @Query("UPDATE Movie m SET m.active = false WHERE m.id = :id")
    int softDeleteById(@Param("id") Long id);

    // single UPDATE for all mutable fields — createdAt intentionally excluded so it is never overwritten
    @Modifying
    @Query("""
            UPDATE Movie m SET
              m.title       = :title,
              m.genre       = :genre,
              m.language    = :language,
              m.duration    = :duration,
              m.rating      = :rating,
              m.releaseDate = :releaseDate,
              m.description = :description,
              m.posterUrl   = :posterUrl,
              m.active      = :active
            WHERE m.id = :id
            """)
    int updateMovieById(
            @Param("id")          Long id,
            @Param("title")       String title,
            @Param("genre")       String genre,
            @Param("language")    String language,
            @Param("duration")    Integer duration,
            @Param("rating")      Double rating,
            @Param("releaseDate") LocalDate releaseDate,
            @Param("description") String description,
            @Param("posterUrl")   String posterUrl,
            @Param("active")      Boolean active
    );
}