package com.theatre.bookingservice.repository;

import com.theatre.bookingservice.entity.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedSeatRepository
        extends JpaRepository<BookedSeat, Long> {

    List<BookedSeat> findByShowIdAndSeatIdIn(
            Long showId,
            List<Long> seatIds
    );
}
