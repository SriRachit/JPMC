package com.jpmc.movietheater.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Enums {
    REMOVE_SECONDS_ENUM("\\s0\\sseconds$"),
    REMOVE_MINUTES_ENUM("\\s0\\sminutes$");

    private final String constant;
}
