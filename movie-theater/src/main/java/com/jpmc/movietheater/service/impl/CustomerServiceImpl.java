package com.jpmc.movietheater.service.impl;

import com.jpmc.movietheater.model.Customer;
import com.jpmc.movietheater.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {
    /**
     * used to store all the customers with email as a key and Customer object as a value
     */
    private static final Map<String, Customer> customers = new HashMap<>();

    /**
     * Method for adding a new customer
     *
     * @param email
     * @param firstName
     * @param lastName
     */
    public void addCustomer(final String email, final String firstName, final String lastName) {
        //adding customer to map
        customers.put(email, new Customer(firstName, lastName, email));
    }

    /**
     * Method to find Customer using customer email
     *
     * @param customerEmail
     * @return Customer object
     */
    public Customer getCustomer(final String customerEmail) {
        return customers.get(customerEmail);
    }

    /**
     * Method to get all customers
     *
     * @return Collection of customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
