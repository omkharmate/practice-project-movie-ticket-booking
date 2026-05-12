package com.theatre.theatreservice.service;


import com.theatre.theatreservice.dto.*;
import com.theatre.theatreservice.entity.Screen;
import com.theatre.theatreservice.entity.Seat;
import com.theatre.theatreservice.exception.ResourceNotFoundException;
import com.theatre.theatreservice.repository.ScreenRepository;
import com.theatre.theatreservice.repository.SeatRepository;
import com.theatre.theatreservice.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    @Override
    public SeatResponse createSeat(SeatRequest request) {

        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found"));

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .seatType(request.getSeatType())
                .screen(screen)
                .build();

        Seat saved = seatRepository.save(seat);

        return mapToResponse(saved);
    }

    @Override
    public List<SeatResponse> getAllSeats() {

        return seatRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SeatResponse mapToResponse(Seat seat) {

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .screenId(seat.getScreen().getId())
                .build();
    }
}
