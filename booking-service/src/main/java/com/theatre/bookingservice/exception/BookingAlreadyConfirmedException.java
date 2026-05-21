package com.theatre.bookingservice.exception;

public class BookingAlreadyConfirmedException extends RuntimeException {
    public BookingAlreadyConfirmedException(
            String message
    ) {
        super(message);
    }}
