package com.currency.currency_analyzer.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class LogService {

    private static final String
DATA_PATH =

        System.getProperty("os.name")
                .toLowerCase()
                .contains("win")

        ?

        "C:/var/data/"

        :

        "/var/data/";

    public void logError(String message) {

        try {

            FileWriter writer =
                    new FileWriter(DATA_PATH
        + "logs.txt",
        true);

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