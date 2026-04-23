package com.example.checkmovieservice;

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

    @Value("${omdb.api.key:YOUR_API_KEY}")
    private String omdbApiKey;

    @GetMapping("/check")
    public String checkMovie(@RequestParam String title) {
        String url = UriComponentsBuilder
                .fromHttpUrl("http://www.omdbapi.com/")
                .queryParam("t", title)
                .queryParam("apikey", omdbApiKey)
                .toUriString();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && "True".equalsIgnoreCase(String.valueOf(response.get("Response")))) {
                return "Movie exists (Version A)";
            }
        } catch (RestClientException ex) {
            // Avoid 500 when OMDb rejects the key or request.
            return "Movie not found (Version A)";
        }

        return "Movie not found (Version A)";
    }
}
