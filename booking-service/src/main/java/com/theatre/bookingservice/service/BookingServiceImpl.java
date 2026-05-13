package com.theatre.bookingservice.service;


import com.theatre.bookingservice.client.TheatreClient;
import com.theatre.bookingservice.dto.BookingRequest;
import com.theatre.bookingservice.dto.BookingResponse;
import com.theatre.bookingservice.dto.SeatResponse;
import com.theatre.bookingservice.dto.ShowResponse;
import com.theatre.bookingservice.entity.BookedSeat;
import com.theatre.bookingservice.entity.Booking;
import com.theatre.bookingservice.entity.BookingStatus;
import com.theatre.bookingservice.exception.ResourceNotFoundException;
import com.theatre.bookingservice.exception.SeatAlreadyBookedException;
import com.theatre.bookingservice.repository.BookedSeatRepository;
import com.theatre.bookingservice.repository.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

        // STEP 1 → VALIDATE SHOW

        ShowResponse show =
                theatreClient.getShow(
                        request.getShowId()
                );

        if (show == null) {

            throw new ResourceNotFoundException(
                    "Show not found"
            );
        }

        // STEP 2 → VALIDATE SEATS

        List<SeatResponse> validatedSeats =
                new ArrayList<>();

        for (Long seatId : request.getSeatIds()) {

            SeatResponse seat =
                    theatreClient.getSeat(seatId);

            if (seat == null) {

                throw new ResourceNotFoundException(
                        "Seat not found with id: "
                                + seatId
                );
            }

            // IMPORTANT VALIDATION
            // seat screen must match show screen

            if (!seat.getScreenId()
                    .equals(show.getScreenId())) {

                throw new RuntimeException(
                        "Seat does not belong to show's screen"
                );
            }

            validatedSeats.add(seat);
        }

        // STEP 3 → CHECK ALREADY BOOKED

        List<BookedSeat> alreadyBookedSeats =
                bookedSeatRepository
                        .findByShowIdAndSeatIdIn(
                                request.getShowId(),
                                request.getSeatIds()
                        );

        if (!alreadyBookedSeats.isEmpty()) {

            throw new SeatAlreadyBookedException(
                    "Some seats are already booked"
            );
        }

        // STEP 4 → CALCULATE TOTAL PRICE

        Double totalPrice =
                show.getPrice()
                        * request.getSeatIds().size();

        // STEP 5 → CREATE BOOKING

        Booking booking =
                Booking.builder()
                        .userId(request.getUserId())
                        .showId(request.getShowId())
                        .bookingStatus(
                                BookingStatus.CONFIRMED
                        )
                        .totalPrice(totalPrice)
                        .bookedAt(
                                LocalDateTime.now()
                        )
                        .build();

        Booking savedBooking =
                bookingRepository.save(booking);

        // STEP 6 → CREATE BOOKED SEATS

        List<BookedSeat> bookedSeats =
                new ArrayList<>();

        for (SeatResponse seat : validatedSeats) {

            BookedSeat bookedSeat =
                    BookedSeat.builder()
                            .seatId(seat.getId())
                            .showId(request.getShowId())
                            .seatNumber(
                                    seat.getSeatNumber()
                            )
                            .status("BOOKED")
                            .booking(savedBooking)
                            .build();

            bookedSeats.add(bookedSeat);
        }

        bookedSeatRepository.saveAll(bookedSeats);

        // STEP 7 → RETURN RESPONSE

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
                .seatIds(
                        request.getSeatIds()
                )
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
    }
}
