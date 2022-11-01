package com.jpmc.movietheater.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reservation {
    private Customer customer;
    private Showing showing;
    private double fee;
    private int audienceCount;
}
