package com.jpmc.movietheater.exception;


import com.jpmc.movietheater.enums.ExceptionEnum;

public class ShowTimeOverlapException extends Exception {

    public static final String MESSAGE = ExceptionEnum.SHOW_TIME_EXCEPTION_ENUM.getMessage();

    public ShowTimeOverlapException() {
        super(MESSAGE);
    }

}
