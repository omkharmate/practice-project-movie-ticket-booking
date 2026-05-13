package com.theatre.theatreservice.service;


import com.theatre.theatreservice.dto.SeatRequest;
import com.theatre.theatreservice.dto.SeatResponse;
import com.theatre.theatreservice.entity.Seat;

import java.util.List;

public interface SeatService {

    Seat getSeatById(Long id);

    SeatResponse createSeat(SeatRequest request);

    List<SeatResponse> getAllSeats();
}
