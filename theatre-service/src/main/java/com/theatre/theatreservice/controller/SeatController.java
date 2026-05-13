package com.theatre.theatreservice.controller;

import com.theatre.theatreservice.dto.SeatRequest;
import com.theatre.theatreservice.dto.SeatResponse;
import com.theatre.theatreservice.entity.Seat;
import com.theatre.theatreservice.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/{id}")
    public SeatResponse getSeatById(
            @PathVariable Long id
    ) {

        Seat seat =
                seatService.getSeatById(id);

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .screenId(
                        seat.getScreen().getId()
                )
                .build();
    }

    @PostMapping
    public SeatResponse createSeat(
            @Valid @RequestBody SeatRequest request) {

        return seatService.createSeat(request);
    }

    @GetMapping
    public List<SeatResponse> getAllSeats() {

        return seatService.getAllSeats();
    }
}