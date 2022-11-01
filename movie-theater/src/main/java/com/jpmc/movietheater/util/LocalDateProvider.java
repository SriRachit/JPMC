package com.jpmc.movietheater.util;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LocalDateProvider {
    private LocalDateProvider(){}
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LocalDate currentDate() {
        return LocalDate.now();
    }
}
