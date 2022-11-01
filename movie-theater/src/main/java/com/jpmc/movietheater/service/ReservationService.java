package com.jpmc.movietheater.service;

import com.jpmc.movietheater.model.Customer;
import com.jpmc.movietheater.model.Reservation;
import com.jpmc.movietheater.model.Showing;

public interface ReservationService {
    Reservation reserveTicket(final Customer customer, final Showing show,
                                     final int audienceCount);
}
