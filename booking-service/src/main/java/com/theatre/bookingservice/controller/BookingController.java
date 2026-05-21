package com.theatre.bookingservice.controller;

import com.theatre.bookingservice.dto.BookingRequest;
import com.theatre.bookingservice.dto.BookingResponse;
import com.theatre.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @RequestBody BookingRequest request
    ) {

        return ResponseEntity.ok(
                bookingService.createBooking(request)
        );
    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponse> confirmBooking(
            @PathVariable Long bookingId
    ) {

        return ResponseEntity.ok(
                bookingService.confirmBooking(
                        bookingId
                )
        );
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
