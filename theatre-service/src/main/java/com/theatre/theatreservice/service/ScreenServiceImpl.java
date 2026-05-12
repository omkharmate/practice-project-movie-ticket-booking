package com.theatre.theatreservice.service;


import com.theatre.theatreservice.dto.*;
import com.theatre.theatreservice.entity.Screen;
import com.theatre.theatreservice.entity.Theatre;
import com.theatre.theatreservice.exception.ResourceNotFoundException;
import com.theatre.theatreservice.repository.ScreenRepository;
import com.theatre.theatreservice.repository.TheatreRepository;
 import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenServiceImpl implements ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    @Override
    public ScreenResponse createScreen(ScreenRequest request) {

        Theatre theatre = theatreRepository.findById(request.getTheatreId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Theatre not found"));

        Screen screen = Screen.builder()
                .name(request.getName())
                .totalSeats(request.getTotalSeats())
                .theatre(theatre)
                .build();

        Screen saved = screenRepository.save(screen);

        return mapToResponse(saved);
    }

    @Override
    public List<ScreenResponse> getAllScreens() {

        return screenRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ScreenResponse mapToResponse(Screen screen) {

        return ScreenResponse.builder()
                .id(screen.getId())
                .name(screen.getName())
                .totalSeats(screen.getTotalSeats())
                .theatreId(screen.getTheatre().getId())
                .build();
    }
}
