package com.theatre.theatreservice.service;

import com.theatre.theatreservice.dto.ShowRequest;
import com.theatre.theatreservice.dto.ShowResponse;

import java.time.LocalDate;
import java.util.List;

public interface ShowService {

    ShowResponse createShow(ShowRequest request);

    ShowResponse getShowById(Long id);

    List<ShowResponse> getShowsByMovie(Long movieId);

    List<ShowResponse> getShowsByMovieAndDate(Long movieId, LocalDate date);

    ShowResponse cancelShow(Long id);
}