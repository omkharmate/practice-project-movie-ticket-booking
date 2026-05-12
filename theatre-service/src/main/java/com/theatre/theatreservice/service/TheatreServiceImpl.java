package com.theatre.theatreservice.service;

import com.theatre.theatreservice.dto.TheatreRequest;
import com.theatre.theatreservice.dto.TheatreResponse;
import com.theatre.theatreservice.entity.Theatre;
import com.theatre.theatreservice.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    @Override
    public TheatreResponse createTheatre(TheatreRequest request) {

        Theatre theatre = Theatre.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .build();

        Theatre saved = theatreRepository.save(theatre);

        return mapToResponse(saved);
    }

    @Override
    public List<TheatreResponse> getAllTheatres() {

        return theatreRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TheatreResponse getTheatreById(Long id) {

        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Theatre not found"));

        return mapToResponse(theatre);
    }

    @Override
    public List<TheatreResponse> getTheatresByCity(String city) {

        return theatreRepository.findByCity(city)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteTheatre(Long id) {

        theatreRepository.deleteById(id);
    }

    private TheatreResponse mapToResponse(Theatre theatre) {

        return TheatreResponse.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .city(theatre.getCity())
                .address(theatre.getAddress())
                .build();
    }
}
