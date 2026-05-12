package com.theatre.theatreservice.repository;

import com.theatre.theatreservice.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    // Find all shows for a specific movie — used by customer browsing
    List<Show> findByMovieId(Long movieId);

    // Find all shows on a specific screen
    List<Show> findByScreenId(Long screenId);

    // Find all shows for a movie on a specific date
    // This is what the customer flow uses:
    // "Show me Pushpa 2 shows on May 15"
    List<Show> findByMovieIdAndShowDate(Long movieId, LocalDate showDate);
}