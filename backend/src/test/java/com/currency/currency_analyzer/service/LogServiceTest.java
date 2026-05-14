package com.currency.currency_analyzer.service;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class LogServiceTest {

    @Test
    void testLogError() {

        LogService service =
                new LogService();

        service.logError(
                "TEST ERROR"
        );

        File file =
                new File(
                        "logs.txt"
                );

        assertTrue(
                file.exists()
        );
    }
}