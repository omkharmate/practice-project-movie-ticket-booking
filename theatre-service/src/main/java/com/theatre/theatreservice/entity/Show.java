package com.theatre.theatreservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "shows")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // We only store the ID — we do NOT join to Movie Service
    // Movie Service owns movie data, we just reference it by ID
    @Column(nullable = false)
    private Long movieId;

    // Which screen is this show playing on
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime showTime;

    @Enumerated(EnumType.STRING)  // saves "SCHEDULED" in DB, not 0/1/2
    @Column(nullable = false)
    private ShowStatus showStatus;

    @Column(nullable = false)
    private Double price;
}