package com.theatre.theatreservice.service;

import com.theatre.theatreservice.dto.TheatreRequest;
import com.theatre.theatreservice.dto.TheatreResponse;

import java.util.List;

public interface TheatreService {

    TheatreResponse createTheatre(TheatreRequest request);

    List<TheatreResponse> getAllTheatres();

    TheatreResponse getTheatreById(Long id);

    List<TheatreResponse> getTheatresByCity(String city);

    void deleteTheatre(Long id);
}
