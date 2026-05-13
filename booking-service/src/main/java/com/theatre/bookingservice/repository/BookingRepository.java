package com.theatre.bookingservice.repository;

 import com.theatre.bookingservice.entity.Booking;
 import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
