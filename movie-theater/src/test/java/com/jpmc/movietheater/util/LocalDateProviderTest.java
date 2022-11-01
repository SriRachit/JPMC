package com.jpmc.movietheater.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class LocalDateProviderTest {
    @Autowired
    LocalDateProvider localDateProvider1;
    @Autowired
    LocalDateProvider localDateProvider2;
    @Autowired
    LocalDateProvider localDateProvider3;

    @Test
    void currentDate() {
        assertEquals(localDateProvider1.hashCode(), localDateProvider2.hashCode());
        assertEquals(localDateProvider2.hashCode(), localDateProvider3.hashCode());
    }
}