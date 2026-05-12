package com.theatre.theatreservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequest {

    @NotBlank
    private String seatNumber;

    @NotBlank
    private String seatType;

    @NotNull
    private Long screenId;
}
