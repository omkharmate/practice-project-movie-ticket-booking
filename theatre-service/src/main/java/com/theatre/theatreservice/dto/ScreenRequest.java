package com.theatre.theatreservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer totalSeats;

    @NotNull
    private Long theatreId;
}
