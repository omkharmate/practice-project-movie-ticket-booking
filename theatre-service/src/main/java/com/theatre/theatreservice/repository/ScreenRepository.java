package com.theatre.theatreservice.repository;


import com.theatre.theatreservice.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
}
