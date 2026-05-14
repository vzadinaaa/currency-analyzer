package com.currency.currency_analyzer.exception;

import com.currency.currency_analyzer.service.LogService;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LogService
            logService;

    public GlobalExceptionHandler(
            LogService logService
    ) {

        this.logService =
                logService;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(
            Exception e
    ) {

        logService.logError(
                e.getMessage()
        );

        return e.getMessage();
    }
}