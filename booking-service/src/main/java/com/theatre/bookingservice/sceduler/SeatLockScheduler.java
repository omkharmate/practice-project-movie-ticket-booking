package com.theatre.bookingservice.sceduler;


import com.theatre.bookingservice.entity.*;
import com.theatre.bookingservice.repository.BookedSeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatLockScheduler {

    private final BookedSeatRepository bookedSeatRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void expireSeatLocks() {

        List<BookedSeat> expiredLocks =

                bookedSeatRepository
                        .findByStatusAndLockExpiresAtBefore(
                                SeatLockStatus.LOCKED,
                                LocalDateTime.now()
                        );

        for (BookedSeat bookedSeat : expiredLocks) {

            bookedSeat.setStatus(
                    SeatLockStatus.EXPIRED
            );

            Booking booking =
                    bookedSeat.getBooking();

            if (booking.getBookingStatus()
                    == BookingStatus.PENDING) {

                booking.setBookingStatus(
                        BookingStatus.EXPIRED
                );
            }
        }

        bookedSeatRepository.saveAll(
                expiredLocks
        );
    }
}
