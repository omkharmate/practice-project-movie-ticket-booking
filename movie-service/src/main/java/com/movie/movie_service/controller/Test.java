package com.movie.movie_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/abc")
    public String Hii(){
        return String.format("hii");
    }
}
