package com.example.checkmovieserviceb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CheckMovieController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${tmdb.api.key:YOUR_API_KEY}")
    private String tmdbApiKey;

    @GetMapping("/check")
    public String checkMovie(@RequestParam String title) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.themoviedb.org/3/search/movie")
                .queryParam("query", title)
                .queryParam("api_key", tmdbApiKey)
                .toUriString();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null) {
                Object resultsObj = response.get("results");
                if (resultsObj instanceof List<?> results && !results.isEmpty()) {
                    return "Movie exists (Version B)";
                }
            }
        } catch (RestClientException ex) {
            // Keep endpoint contract stable even when TMDb rejects the request.
            return "Movie not found (Version B)";
        }

        return "Movie not found (Version B)";
    }
}
