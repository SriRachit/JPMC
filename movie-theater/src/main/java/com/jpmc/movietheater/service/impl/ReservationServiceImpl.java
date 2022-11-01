package com.jpmc.movietheater.service.impl;

import com.jpmc.movietheater.model.Customer;
import com.jpmc.movietheater.model.Reservation;
import com.jpmc.movietheater.model.Showing;
import com.jpmc.movietheater.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class ReservationServiceImpl implements ReservationService {
    /**
     * Used to store all the reservations made
     */
    private static final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    /**
     * Method used to create new reservation for a specific show and specific customer
     * passed as a parameter
     *
     * @param customer
     * @param show
     * @param audienceCount
     * @return Reservation
     */
    @Override
    public Reservation reserveTicket(final Customer customer, final Showing show, final int audienceCount) {
        double ticketPrice = show.getMovie().getTicketPrice();
        final Reservation reservation = new Reservation(customer, show, ticketPrice * audienceCount, audienceCount);
        Collection<Reservation> customerReservations = getCustomersReservation(customer);
        if (customerReservations == null) {
            customerReservations = new LinkedList<>();
        }
        customerReservations.add(reservation);
        reservations.put(customer.getEmail(), customerReservations);
        return reservation;
    }

    /**
     * Method to get all the reservations made for a customer passing
     * Customer object as a parameter
     *
     * @param customer
     * @return Collection of reservation
     */
    public Collection<Reservation> getCustomersReservation(final Customer customer) {
        return reservations.get(customer.getEmail());
    }

    /**
     * Method to get all the reservations made for a customer passing
     * Customer email as a parameter
     *
     * @param email
     * @return Collection of reservation
     */
    public Collection<Reservation> getCustomersReservation(final String email) {
        return reservations.get(email);
    }

    /**
     * Method to get all the reservations made for all customer
     *
     * @param
     * @return Collection of reservation
     */
    private Collection<Reservation> getAllReservations() {
        final Collection<Reservation> allReservations = new LinkedList<>();

        for (Collection<Reservation> reservation : reservations.values()) {
            allReservations.addAll(reservation);
        }
        return allReservations;
    }
}
