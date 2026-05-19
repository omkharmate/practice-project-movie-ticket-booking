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

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT bs
            FROM BookedSeat bs
            WHERE bs.showId = :showId
            AND bs.seatId IN :seatIds
            AND (
                    bs.status = com.theatre.bookingservice.entity.SeatLockStatus.BOOKED
                    OR (
                        bs.status = com.theatre.bookingservice.entity.SeatLockStatus.LOCKED
                        AND bs.lockExpiresAt > :currentTime
                    )
            )
            """)
    List<BookedSeat> findActiveSeatLocks(
            @Param("showId") Long showId,
            @Param("seatIds") List<Long> seatIds,
            @Param("currentTime") LocalDateTime currentTime
    );

    List<BookedSeat> findByStatusAndLockExpiresAtBefore(
            SeatLockStatus status,
            LocalDateTime time
    );

    List<BookedSeat> findByBookingId(Long bookingId);
}