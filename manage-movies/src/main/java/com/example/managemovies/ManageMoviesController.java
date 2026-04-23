package com.example.managemovies;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageMoviesController {

    private final MovieClient movieClient;

    public ManageMoviesController(MovieClient movieClient) {
        this.movieClient = movieClient;
    }

    @GetMapping("/insert")
    public String insertMovie(@RequestParam String title) {
        String response = movieClient.checkMovie(title);

        if (response != null && response.toLowerCase().contains("movie exists")) {
            return "Movie validated. Ready to insert into Database. Response: " + response;
        }

        return "Cannot insert: Movie not found";
    }
}
