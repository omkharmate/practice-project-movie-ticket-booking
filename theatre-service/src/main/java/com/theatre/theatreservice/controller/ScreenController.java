package com.theatre.theatreservice.controller;

import com.theatre.theatreservice.dto.ScreenRequest;
import com.theatre.theatreservice.dto.ScreenResponse;
import com.theatre.theatreservice.service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping
    public ScreenResponse createScreen(
            @Valid @RequestBody ScreenRequest request) {

        return screenService.createScreen(request);
    }

    @GetMapping
    public List<ScreenResponse> getAllScreens() {

        return screenService.getAllScreens();
    }
}