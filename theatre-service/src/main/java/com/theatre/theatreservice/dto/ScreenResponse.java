package com.theatre.theatreservice.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenResponse {

    private Long id;
    private String name;
    private Integer totalSeats;
    private Long theatreId;
}
