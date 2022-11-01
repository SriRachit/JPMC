package com.jpmc.movietheater.exception;


import com.jpmc.movietheater.enums.ExceptionEnum;

public class JSONParseException extends Exception {

    public static final String MESSAGE = ExceptionEnum.INVALID_JSON_EXCEPTION_ENUM.getMessage();

    public JSONParseException() {
        super(MESSAGE);
    }
}
