package com.currency.currency_analyzer.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LogService {

    public void logError(String message) {

        try {

            FileWriter writer =
                    new FileWriter("logs.txt", true);

            writer.write(
                    LocalDateTime.now()
                            + " ERROR: "
                            + message
                            + "\n"
            );

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}