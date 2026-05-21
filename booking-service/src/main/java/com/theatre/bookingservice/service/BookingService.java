package com.theatre.bookingservice.service;

import com.theatre.bookingservice.dto.BookingRequest;
import com.theatre.bookingservice.dto.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(
            BookingRequest request
    );

    BookingResponse confirmBooking(
            Long bookingId
    );

    BookingResponse getBookingById(
            Long id
    );

    void cancelBooking(
            Long id
    );
}