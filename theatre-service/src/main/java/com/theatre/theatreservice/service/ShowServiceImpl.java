package com.theatre.theatreservice.service;

import com.theatre.theatreservice.dto.ShowRequest;
import com.theatre.theatreservice.dto.ShowResponse;
import com.theatre.theatreservice.entity.Screen;
import com.theatre.theatreservice.entity.Show;
import com.theatre.theatreservice.entity.ShowStatus;
import com.theatre.theatreservice.exception.ResourceNotFoundException;
import com.theatre.theatreservice.repository.ScreenRepository;
import com.theatre.theatreservice.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final ScreenRepository screenRepository;

    @Override
    public ShowResponse createShow(ShowRequest request) {

        // Validate screen exists
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Screen not found with id: "
                                + request.getScreenId()));

        Show show = Show.builder()
                .movieId(request.getMovieId())
                .screen(screen)
                .showDate(request.getShowDate())
                .showTime(request.getShowTime())
                .showStatus(ShowStatus.SCHEDULED) // always starts as SCHEDULED
                .price(request.getPrice())
                .build();

        Show saved = showRepository.save(show);
        return mapToResponse(saved);
    }

    @Override
    public ShowResponse getShowById(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found with id: " + id));

        return mapToResponse(show);
    }

    @Override
    public List<ShowResponse> getShowsByMovie(Long movieId) {

        return showRepository.findByMovieId(movieId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ShowResponse> getShowsByMovieAndDate(Long movieId, LocalDate date) {

        return showRepository.findByMovieIdAndShowDate(movieId, date)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ShowResponse cancelShow(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Show not found with id: " + id));

        // Only SCHEDULED shows can be cancelled
        if (show.getShowStatus() == ShowStatus.CANCELLED) {
            throw new RuntimeException("Show is already cancelled");
        }

        show.setShowStatus(ShowStatus.CANCELLED);
        Show saved = showRepository.save(show);
        return mapToResponse(saved);
    }

    // Private helper — converts entity to response DTO
    private ShowResponse mapToResponse(Show show) {

        return ShowResponse.builder()
                .id(show.getId())
                .movieId(show.getMovieId())
                .screenId(show.getScreen().getId())
                .screenName(show.getScreen().getName())
                .theatreName(show.getScreen().getTheatre().getName())
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .showStatus(show.getShowStatus())
                .price(show.getPrice())
                .build();
    }
}