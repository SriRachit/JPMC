package com.jpmc.movietheater.service;

import com.jpmc.movietheater.model.Customer;

import java.util.Collection;

public interface CustomerService {
    void addCustomer(final String email, final String firstName, final String lastName);

    Customer getCustomer(final String customerEmail);

    Collection<Customer> getAllCustomers();
}
