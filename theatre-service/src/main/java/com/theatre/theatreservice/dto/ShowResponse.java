package com.theatre.theatreservice.dto;

import com.theatre.theatreservice.entity.ShowStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowResponse {

    private Long id;
    private Long movieId;
    private Long screenId;

    // We also return screen name and theatre name
    // so the caller does not need to make another API call
    private String screenName;
    private String theatreName;

    private LocalDate showDate;
    private LocalTime showTime;
    private ShowStatus showStatus;
    private Double price;
}