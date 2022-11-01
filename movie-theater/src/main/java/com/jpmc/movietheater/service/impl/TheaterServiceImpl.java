package com.jpmc.movietheater.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.movietheater.enums.Enums;
import com.jpmc.movietheater.exception.JSONParseException;
import com.jpmc.movietheater.exception.ShowTimeOverlapException;
import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.service.TheaterService;
import com.jpmc.movietheater.util.JsonUtil;
import com.jpmc.movietheater.util.LocalDateProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    LocalDateProvider localDateProvider;
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    MovieServiceImpl movieService;

    private final Map<LocalDate, List<Showing>> schedule = new LinkedHashMap<>();

    /**
     * This method add a show to the theater and sort them by show start time
     * and checks if the show is overlapping another show and throws exception
     * if the show overlaps another show assuming there is only a single screen
     * in the theater and saves the sorted list of shows in the map where the
     * key is the date for the show and assigns the show sequence according to
     * sorted shows by show start time.
     *
     * @param show
     * @throws ShowTimeOverlapException
     */
    @Override
    public void addSchedule(final Showing show) throws ShowTimeOverlapException {
        log.info(getClass() + "-> addSchedule-> show start time: {}, movie title: {} ", show.getShowStartTime(), show.getMovie().getTitle());
        AtomicInteger count = new AtomicInteger();
        count.getAndIncrement();
        LocalDate date = show.getShowStartTime().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Showing> list = schedule.getOrDefault(date, new ArrayList<>());
        if (!checkShowTime(list, show)) throw new ShowTimeOverlapException();
        list.add(show);
        list = list
                .stream()
                .sorted(Comparator
                        .comparing(Showing::getShowStartTime))
                .collect(Collectors.toList())
                .stream().peek(x -> x.setSequenceOfTheDay(count.getAndIncrement()))
                .collect(Collectors.toList());

        this.schedule.put(date, list);
    }

    /**
     * This method print show schedule in a human-readable format date wise
     */
    @Override
    public void printSchedule() {
        schedule.forEach((key, value) -> {
            System.out.println("========================" + key + "========================");
            value
                    .forEach(s -> System.out.println(s.getSequenceOfTheDay() + ": " + s.getShowStartTime() + " " + s.getMovie().getTitle() + " " + s.getMovie().getDescription() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + movieService.getMovieFee(s)));
        });
        System.out.println("==========================END==========================");
    }

    /**
     * This method print show schedule in a JSON format date wise
     */
    public void printScheduleJson() {
        AtomicReference<List<Object>> jsonObjects = new AtomicReference<>();
        schedule.forEach((key, value) -> {
            System.out.println("========================" + key + "========================");
            try {
                jsonObjects.set(jsonUtil.getJsonObjectList(value));
                System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonObjects));
            } catch (JSONParseException | JsonProcessingException e) {
                System.out.println("Exception while parsing JSON");
            }
        });
        System.out.println("==========================END==========================");
    }

    /**
     * This method transforms Duration to human-readable time handling purals
     *
     * @param duration
     * @return String of time in hours and minutes
     */
    @Override
    public String humanReadableFormat(Duration duration) {
        return DurationFormatUtils.formatDurationWords(duration.toMillis(), true, false).replaceAll(Enums.REMOVE_SECONDS_ENUM.getConstant(), "").replaceAll(Enums.REMOVE_MINUTES_ENUM.getConstant(), "");
    }

    /**
     * This method get the shows running on a given date
     *
     * @param showDate
     * @return List of shows on given date
     */
    @Override
    public List<Showing> getShowsByDate(LocalDate showDate) {
        return schedule.get(showDate);
    }

    /**
     * This method checks if the show overlaps with existing scheduled shows on a
     * given date new show needed to be scheduled
     *
     * @param shows
     * @param newShow
     * @return boolean value if the show-time is valid and can be accommodated with scheduled shows
     */
    private boolean checkShowTime(final List<Showing> shows, Showing newShow) {

        List<Showing> listCopy = new ArrayList<>(shows);
        listCopy.add(newShow);
        LocalDateTime showEndTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        List<Showing> sortedShowList = listCopy
                .stream()
                .sorted(Comparator
                        .comparing(Showing::getShowStartTime))
                .collect(Collectors.toList());
        for (Showing show : sortedShowList) {
            if (!show.getShowStartTime().isAfter(showEndTime)) return false;
            showEndTime = show.getShowStartTime().plusMinutes(show.getMovie().getRunningTime().toMinutes());
        }
        return true;
    }
}