package com.jpmc.movietheater.service.impl;

import com.jpmc.movietheater.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerServiceImplTest {
    @Autowired
    private CustomerServiceImpl customerService;

    @Test
    void addCustomer() {
        customerService.addCustomer("john@gmail.com", "John", "Doe");
        Customer customer = customerService.getCustomer("john@gmail.com");
        assertEquals("john@gmail.com", customer.getEmail());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
    }
}