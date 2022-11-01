package com.jpmc.movietheater.service;

import com.jpmc.movietheater.exception.ShowTimeOverlapException;
import com.jpmc.movietheater.model.Showing;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface TheaterService {
    void addSchedule(Showing Showing) throws ShowTimeOverlapException;

    void printSchedule();

    String humanReadableFormat(Duration duration);

    List<Showing> getShowsByDate(LocalDate showDate);
}
