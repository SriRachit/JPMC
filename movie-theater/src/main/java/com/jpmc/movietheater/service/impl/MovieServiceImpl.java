package com.jpmc.movietheater.service.impl;

import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.model.movie.Movie;
import com.jpmc.movietheater.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Value("${movie.discount.code}")
    private int specialDiscountCode;
    @Value("${movie.discount.day}")
    private int dayDiscount;
    /**
     * Used to store all the Movies created and added
     */
    private static final Map<String, Movie> movies = new HashMap<>();
    private static final int SEQUENCE_ONE = 1;
    private static final int DISCOUNT_SEQUENCE_ONE = 3;
    private static final int SEQUENCE_TWO = 2;
    private static final int DISCOUNT_SEQUENCE_TWO = 2;

    /**
     * Method to add new movie
     * @param movie
     */
    @Override
    public void addMovie(final Movie movie) {
        movies.put(movie.getTitle(), movie);
    }

    /**
     * Method to get the maximum discount applicable on the show
     * @param show
     * @return max elligible discount
     */
    @Override
    public double getDiscount(Showing show) {
        double codeDisc = (show.getMovie().getSpecialCode() == specialDiscountCode) ? show.getMovie().getTicketPrice() * .2 : 0;
        double sequenceDisc = getSequenceDiscount(show.getSequenceOfTheDay());
        double showTimeDisc = getShowTimeDiscount(show);
        double showDayDisc = (show.getShowStartTime().getDayOfMonth() == dayDiscount) ? 1 : 0;
        return Math.max(Math.max(codeDisc, sequenceDisc), Math.max(showTimeDisc, showDayDisc));
    }

    /**
     * Method to calculate movie fee based on discount applicable
     * on the specific movie, show and timings
     * @param show
     * @return applicable movie fee
     */
    @Override
    public double getMovieFee(Showing show) {
        return show.getMovie().getTicketPrice() - getDiscount(show);
    }

    /**
     * Method to calculate discount on the movie
     * applicable on the basis of show order
     * @param sequence
     * @return discount applicable
     */
    private double getSequenceDiscount(int sequence) {
        if (sequence == SEQUENCE_ONE)
            return DISCOUNT_SEQUENCE_ONE;
        else if (sequence == SEQUENCE_TWO)
            return DISCOUNT_SEQUENCE_TWO;
        else return 0;
    }

    /**
     * Method to calculate discount on the movie
     * applicable on the basis of show timing
     * @param show
     * @return discount applicable
     */
    private double getShowTimeDiscount(Showing show) {
        if (show.getShowStartTime().toLocalTime().isAfter(LocalTime.of(10, 59, 59)) && show.getShowStartTime().toLocalTime().isBefore(LocalTime.of(16, 00, 01)))
            return show.getMovie().getTicketPrice() * 0.25;
        return 0;
    }
}
