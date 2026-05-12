package com.theatre.theatreservice.controller;

import com.theatre.theatreservice.dto.TheatreRequest;
import com.theatre.theatreservice.dto.TheatreResponse;
import com.theatre.theatreservice.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping
    public TheatreResponse createTheatre(
            @Valid @RequestBody TheatreRequest request) {

        return theatreService.createTheatre(request);
    }

    @GetMapping
    public List<TheatreResponse> getAllTheatres() {

        return theatreService.getAllTheatres();
    }

    @GetMapping("/{id}")
    public TheatreResponse getTheatreById(@PathVariable Long id) {

        return theatreService.getTheatreById(id);
    }

    @GetMapping("/city/{city}")
    public List<TheatreResponse> getTheatresByCity(
            @PathVariable String city) {

        return theatreService.getTheatresByCity(city);
    }

    @DeleteMapping("/{id}")
    public void deleteTheatre(@PathVariable Long id) {

        theatreService.deleteTheatre(id);
    }
}
