package com.theatre.bookingservice.controller;

import com.theatre.bookingservice.dto.BookingRequest;
import com.theatre.bookingservice.dto.BookingResponse;
import com.theatre.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse createBooking(
            @RequestBody BookingRequest request
    ) {

        return bookingService.createBooking(request);
    }

    @GetMapping("/{id}")
    public BookingResponse getBooking(
            @PathVariable Long id
    ) {

        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(
            @PathVariable Long id
    ) {

        bookingService.cancelBooking(id);
    }
}
