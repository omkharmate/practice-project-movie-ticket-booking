package com.theatre.theatreservice.service;


import com.theatre.theatreservice.dto.SeatRequest;
import com.theatre.theatreservice.dto.SeatResponse;

import java.util.List;

public interface SeatService {

    SeatResponse createSeat(SeatRequest request);

    List<SeatResponse> getAllSeats();
}
