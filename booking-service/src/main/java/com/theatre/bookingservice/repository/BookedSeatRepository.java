package com.theatre.bookingservice.repository;

import com.theatre.bookingservice.entity.BookedSeat;
import com.theatre.bookingservice.entity.SeatLockStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookedSeatRepository
        extends JpaRepository<BookedSeat, Long> {

    @Query("""
       SELECT bs
       FROM BookedSeat bs
       WHERE bs.showId = :showId
       AND bs.seatId IN :seatIds
       AND (
            bs.status = 'BOOKED'
            OR
            (
                bs.status = 'LOCKED'
                AND bs.lockExpiresAt > :currentTime
            )
       )
       """)
    List<BookedSeat> findActiveSeatLocks(
            Long showId,
            List<Long> seatIds,
            LocalDateTime currentTime
    );


    List<BookedSeat> findByStatusAndLockExpiresAtBefore(
            SeatLockStatus status,
            LocalDateTime time
    );

    List<BookedSeat> findByBookingId(Long bookingId);
}