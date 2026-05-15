package com.theatre.bookingservice.exception;

// This exception is thrown when a downstream service (theatre-service)
// is unreachable. It is caught by GlobalExceptionHandler and returned
// as a clean 503 response to the client.
public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
