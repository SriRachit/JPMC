package com.jpmc.movietheater.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionEnum {

    SHOW_TIME_EXCEPTION_ENUM("Invalid show time exception"),
    INVALID_JSON_EXCEPTION_ENUM("Exception occurred while parsing JSON");

    private final String message;
}
