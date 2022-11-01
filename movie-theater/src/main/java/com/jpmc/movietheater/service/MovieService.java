package com.jpmc.movietheater.service;

import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.model.movie.Movie;

public interface MovieService {
    void addMovie(final Movie movie);
    double getDiscount(Showing show);
    double getMovieFee(Showing show);
}
