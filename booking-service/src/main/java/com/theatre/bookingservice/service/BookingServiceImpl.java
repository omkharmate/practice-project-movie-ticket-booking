package com.theatre.bookingservice.service;

 import com.theatre.bookingservice.client.TheatreClient;
 import com.theatre.bookingservice.dto.BookingRequest;
import com.theatre.bookingservice.dto.BookingResponse;
 import com.theatre.bookingservice.dto.ShowResponse;
 import com.theatre.bookingservice.entity.BookedSeat;
import com.theatre.bookingservice.entity.Booking;
import com.theatre.bookingservice.entity.BookingStatus;
import com.theatre.bookingservice.entity.SeatLockStatus;
import com.theatre.bookingservice.exception.BookingAlreadyConfirmedException;
import com.theatre.bookingservice.exception.ResourceNotFoundException;
import com.theatre.bookingservice.exception.SeatAlreadyLockedException;
import com.theatre.bookingservice.repository.BookedSeatRepository;
import com.theatre.bookingservice.repository.BookingRepository;
 import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl
        implements BookingService {

    private final BookingRepository bookingRepository;

     private final BookedSeatRepository bookedSeatRepository;

    private final TheatreClient theatreClient;

    @Override
    @Transactional
    public BookingResponse createBooking(
            BookingRequest request
    ) {

        ShowResponse show = theatreClient.getShow(request.getShowId());

        log.info(
                "Creating booking for userId={} showId={} seats={}",
                request.getUserId(),
                request.getShowId(),
                request.getSeatIds()
        );

        if (request.getSeatIds() == null ||
                request.getSeatIds().isEmpty()) {

            throw new IllegalArgumentException(
                    "Seat list cannot be empty"
            );
        }

        LocalDateTime now =
                LocalDateTime.now();

        List<BookedSeat> existingLocks =
                bookedSeatRepository.findActiveSeatLocks(
                        request.getShowId(),
                        request.getSeatIds(),
                        now
                );

        if (!existingLocks.isEmpty()) {

            log.error(
                    "Seats already locked for showId={} seats={}",
                    request.getShowId(),
                    request.getSeatIds()
            );

            throw new SeatAlreadyLockedException(
                    "Seat already locked/booked"
            );
        }

        BigDecimal totalPrice = BigDecimal.valueOf(request.getSeatIds().size() * show.getPrice());


        Booking booking =
                Booking.builder()
                        .userId(request.getUserId())
                        .showId(request.getShowId())
                        .bookingStatus(
                                BookingStatus.PENDING
                        )
                        .totalPrice(totalPrice)
                        .build();

        Booking savedBooking =
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

        savedBooking.setBookedSeats(bookedSeats);

        log.info(
                "Booking created successfully bookingId={}",
                savedBooking.getId()
        );

        return BookingResponse.builder()
                .bookingId(savedBooking.getId())
                .userId(savedBooking.getUserId())
                .showId(savedBooking.getShowId())
                .bookingStatus(
                        savedBooking.getBookingStatus()
                )
                .totalPrice(
                        savedBooking.getTotalPrice()
                )
                .bookedAt(
                        savedBooking.getBookedAt()
                )
                .seatIds(request.getSeatIds())
                .lockExpiresAt(expiryTime)
                .build();
    }

    @Override
    @Transactional
    public BookingResponse confirmBooking(
            Long bookingId
    ) {

        log.info(
                "Confirming booking bookingId={}",
                bookingId
        );

        Booking booking =
                bookingRepository.findById(bookingId)
                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );

        if (booking.getBookingStatus() ==
                BookingStatus.CONFIRMED) {

            throw new BookingAlreadyConfirmedException(
                    "Booking already confirmed"
            );
        }

        booking.setBookingStatus(
                BookingStatus.CONFIRMED
        );

        booking.setBookedAt(
                LocalDateTime.now()
        );

        booking.getBookedSeats()
                .forEach(seat ->

                        seat.setStatus(
                                SeatLockStatus.BOOKED
                        )

                );

        bookingRepository.save(booking);

        log.info(
                "Booking confirmed bookingId={}",
                bookingId
        );

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .showId(booking.getShowId())
                .bookingStatus(
                        booking.getBookingStatus()
                )
                .totalPrice(
                        booking.getTotalPrice()
                )
                .bookedAt(
                        booking.getBookedAt()
                )
                .seatIds(

                        booking.getBookedSeats()
                                .stream()
                                .map(BookedSeat::getSeatId)
                                .toList()

                )
                .build();
    }

    @Override
    public BookingResponse getBookingById(
            Long id
    ) {

        log.info(
                "Fetching booking bookingId={}",
                id
        );

        Booking booking =
                bookingRepository.findById(id)
                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );

        List<Long> seatIds =
                booking.getBookedSeats()
                        .stream()
                        .map(BookedSeat::getSeatId)
                        .toList();

        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userId(booking.getUserId())
                .showId(booking.getShowId())
                .bookingStatus(
                        booking.getBookingStatus()
                )
                .totalPrice(
                        booking.getTotalPrice()
                )
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

        log.info(
                "Cancelling booking bookingId={}",
                id
        );

        Booking booking =
                bookingRepository.findById(id)
                        .orElseThrow(() ->

                                new ResourceNotFoundException(
                                        "Booking not found"
                                )
                        );

        booking.setBookingStatus(
                BookingStatus.CANCELLED
        );

        booking.getBookedSeats()
                .forEach(seat ->

                        seat.setStatus(
                                SeatLockStatus.LOCKED
                        )

                );

        bookingRepository.save(booking);

        log.info(
                "Booking cancelled bookingId={}",
                id
        );
    }
}