package com.example.checkmovieserviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CheckMovieServiceBApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckMovieServiceBApplication.class, args);
    }
}
