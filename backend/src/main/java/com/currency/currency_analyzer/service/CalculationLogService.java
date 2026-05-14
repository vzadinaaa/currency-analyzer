package com.currency.currency_analyzer.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CalculationLogService {

    public void saveCalculation(

            String base,

            String symbols,

            String strongest,

            String weakest,

            double average
    ) {

        try {

            FileWriter writer =
                    new FileWriter(
                            "calculations.txt",
                            true
                    );

            writer.write(
                    "========================\n"
            );

            writer.write(
                    "Time: "
                    + LocalDateTime.now()
                    + "\n"
            );

            writer.write(
                    "Base currency: "
                    + base
                    + "\n"
            );

            writer.write(
                    "Selected currencies: "
                    + symbols
                    + "\n"
            );

            writer.write(
                    "Strongest currency: "
                    + strongest
                    + "\n"
            );

            writer.write(
                    "Weakest currency: "
                    + weakest
                    + "\n"
            );

            writer.write(
                    "Average: "
                    + average
                    + "\n\n"
            );

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}