package com.jpmc.movietheater.service.impl;

import com.jpmc.movietheater.exception.ShowTimeOverlapException;
import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.model.movie.Movie;
import com.jpmc.movietheater.util.LocalDateProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class MovieServiceImplTest {
    @Autowired
    MovieServiceImpl movieService;
    @Autowired
    TheaterServiceImpl theaterService;
    @Autowired
    LocalDateProvider provider;

    @BeforeEach
    public void setup() throws ShowTimeOverlapException {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "spiderMan Description", Duration.ofMinutes(50), 12, 1);
        Movie pk = new Movie("PK", "pk Description", Duration.ofMinutes(55), 11, 0);
        Movie superMan = new Movie("superMan", "superMan Description", Duration.ofMinutes(40), 9, 0);
        theaterService.addSchedule(new Showing(pk, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))));
        theaterService.addSchedule(new Showing(superMan, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))));
        theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))));
        theaterService.addSchedule(new Showing(pk, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))));
        theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))));
        theaterService.addSchedule(new Showing(superMan, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))));
        theaterService.addSchedule(new Showing(pk, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))));
        theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))));
    }

    @Test
    void getMovieFee() {
        List<Showing> list = theaterService.getShowsByDate(provider.currentDate());
        assertEquals(8, movieService.getMovieFee(list.get(0)));
        assertEquals(6.75, movieService.getMovieFee(list.get(1)));
        assertEquals(9, movieService.getMovieFee(list.get(2)));
        assertEquals(8.25, movieService.getMovieFee(list.get(3)));
        assertEquals(9.6, movieService.getMovieFee(list.get(4)));
        assertEquals(9, movieService.getMovieFee(list.get(5)));
        assertEquals(11, movieService.getMovieFee(list.get(6)));
        assertEquals(9.6, movieService.getMovieFee(list.get(7)));
    }
}