package com.jpmc.movietheater.service.impl;

import com.jpmc.movietheater.exception.ShowTimeOverlapException;
import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.model.movie.Movie;
import com.jpmc.movietheater.util.LocalDateProvider;
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
class TheaterServiceImplTest {

    @Autowired
    TheaterServiceImpl theaterService;
    @Autowired
    LocalDateProvider provider;

    @Test
    void addSchedule() throws ShowTimeOverlapException {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "spiderMan Description", Duration.ofMinutes(50), 12, 1);
        Movie pk = new Movie("PK", "pk Description", Duration.ofMinutes(55), 11, 0);
        Movie superMan = new Movie("superMan", "superMan Description", Duration.ofMinutes(40), 9, 0);
        theaterService.addSchedule(new Showing(pk, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))));
        theaterService.addSchedule(new Showing(superMan, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))));
        theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))));
        theaterService.addSchedule(new Showing(pk, LocalDateTime.of(provider.currentDate().plusDays(1), LocalTime.of(14, 30))));
        theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(provider.currentDate().plusDays(1), LocalTime.of(16, 10))));
        theaterService.addSchedule(new Showing(superMan, LocalDateTime.of(provider.currentDate().plusDays(1), LocalTime.of(17, 50))));
        theaterService.addSchedule(new Showing(pk, LocalDateTime.of(provider.currentDate().plusDays(2), LocalTime.of(19, 30))));
        theaterService.addSchedule(new Showing(spiderMan, LocalDateTime.of(provider.currentDate().plusDays(2), LocalTime.of(21, 10))));

        List<Showing> showings1 = theaterService.getShowsByDate(provider.currentDate());
        List<Showing> showings2 = theaterService.getShowsByDate(provider.currentDate().plusDays(1));
        List<Showing> showings3 = theaterService.getShowsByDate(provider.currentDate().plusDays(2));
        assertEquals(3, showings1.size());
        assertEquals(3, showings2.size());
        assertEquals(2, showings3.size());
    }
}