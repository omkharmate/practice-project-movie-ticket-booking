package com.theatre.bookingservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowResponse {

    private Long id;

    private Long movieId;

    private Long screenId;

    private String showStatus;

    private Double price;
}
