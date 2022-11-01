package com.jpmc.movietheater.model.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
@Builder
public class Movie {
    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;
}
