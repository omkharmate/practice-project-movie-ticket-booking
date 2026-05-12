package com.theatre.theatreservice.service;


import com.theatre.theatreservice.dto.ScreenRequest;
import com.theatre.theatreservice.dto.ScreenResponse;

import java.util.List;

public interface ScreenService {

    ScreenResponse createScreen(ScreenRequest request);

    List<ScreenResponse> getAllScreens();
}
