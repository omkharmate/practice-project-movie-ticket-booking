package com.theatre.theatreservice.repository;


import com.theatre.theatreservice.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
