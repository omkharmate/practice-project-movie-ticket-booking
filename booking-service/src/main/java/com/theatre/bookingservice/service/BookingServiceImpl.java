package com.theatre.bookingservice.service;


import com.theatre.bookingservice.client.TheatreClient;
import com.theatre.bookingservice.dto.BookingRequest;
import com.theatre.bookingservice.dto.BookingResponse;
import com.theatre.bookingservice.entity.BookedSeat;
import com.theatre.bookingservice.entity.Booking;
import com.theatre.bookingservice.entity.BookingStatus;
import com.theatre.bookingservice.entity.SeatLockStatus;
import com.theatre.bookingservice.exception.ResourceNotFoundException;
import com.theatre.bookingservice.repository.BookedSeatRepository;
import com.theatre.bookingservice.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.theatre.bookingservice.dto.BookingResponse.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl
        implements BookingService {

    private final BookingRepository bookingRepository;

    private final BookedSeatRepository bookedSeatRepository;

    private final TheatreClient theatreClient;

    @Transactional
    @Override
    public void confirmBooking(Long bookingId) {

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Booking not found"
                                )
                        );

        if (booking.getBookingStatus()
                != BookingStatus.PENDING) {

            throw new RuntimeException(
                    "Booking already processed"
            );
        }

        booking.setBookingStatus(
                BookingStatus.CONFIRMED
        );

        List<BookedSeat> bookedSeats =
                bookedSeatRepository
                        .findByBookingId(bookingId);

        for (BookedSeat bookedSeat : bookedSeats) {

            bookedSeat.setStatus(
                    SeatLockStatus.BOOKED
            );
        }

        bookedSeatRepository.saveAll(bookedSeats);

        bookingRepository.save(booking);
    }

    @Transactional
    @Override
    public BookingResponse createBooking(
            BookingRequest request
    ) {

        LocalDateTime now = LocalDateTime.now();

        List<BookedSeat> existingLocks =
                bookedSeatRepository.findActiveSeatLocks(
                        request.getShowId(),
                        request.getSeatIds(),
                        now
                );

        if (!existingLocks.isEmpty()) {

            throw new RuntimeException(
                    "Seat already locked/booked"
            );
        }

        Booking booking =
                Booking.builder()
                        .userId(request.getUserId())
                        .showId(request.getShowId())
                        .bookingStatus(BookingStatus.PENDING)
                        .build();

        final Booking savedBooking =
                bookingRepository.save(booking);
        LocalDateTime expiryTime =
                now.plusMinutes(5);

        List<BookedSeat> bookedSeats =
                request.getSeatIds()
                        .stream()
                        .map(seatId ->

                                BookedSeat.builder()
                                        .seatId(seatId)
                                        .showId(request.getShowId())
                                        .status(
                                                SeatLockStatus.LOCKED
                                        )
                                        .lockExpiresAt(expiryTime)
                                        .booking(savedBooking)
                                        .build()

                        ).toList();

        bookedSeatRepository.saveAll(bookedSeats);

        return builder()
                .bookingId(savedBooking.getId())
                .status("PENDING")
                .lockExpiresAt(expiryTime)
                .build();
    }

    @Override
    public BookingResponse getBookingById(
            Long id
    ) {

        Booking booking =
                bookingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found"
                                ));

        List<Long> seatIds =
                booking.getBookedSeats()
                        .stream()
                        .map(BookedSeat::getSeatId)
                        .toList();

        return builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .showId(booking.getShowId())
                .bookingStatus(
                        booking.getBookingStatus()
                )
                .totalPrice(booking.getTotalPrice())
                .bookedAt(
                        booking.getBookedAt()
                )
                .seatIds(seatIds)
                .build();
    }
    @Override
    @Transactional
    public void cancelBooking(
            Long id
    ) {
        log.info("Cancelling bookingId={}", id);

        Booking booking =
                bookingRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Booking not found"
                                ));

        booking.setBookingStatus(
                BookingStatus.CANCELLED
        );

        bookingRepository.save(booking);

        bookedSeatRepository.deleteAll(
                booking.getBookedSeats()
        );
        log.info("Booking cancelled — bookingId={}", id);
    }
}
