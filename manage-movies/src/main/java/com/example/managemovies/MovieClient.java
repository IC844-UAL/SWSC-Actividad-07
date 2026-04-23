package com.example.managemovies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "check-movie-service")
public interface MovieClient {

    @GetMapping("/check")
    String checkMovie(@RequestParam("title") String title);
}
