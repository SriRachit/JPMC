package com.jpmc.movietheater.model;

import com.jpmc.movietheater.model.movie.Movie;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public Showing(final Movie movie, final LocalDateTime showStartTime) {
        this.movie = Movie.builder()
                .description(movie.getDescription())
                .runningTime(movie.getRunningTime())
                .title(movie.getTitle())
                .specialCode(movie.getSpecialCode())
                .ticketPrice(movie.getTicketPrice())
                .build();
        this.showStartTime = showStartTime;
    }
}
