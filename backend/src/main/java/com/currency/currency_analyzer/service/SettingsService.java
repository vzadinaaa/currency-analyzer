package com.currency.currency_analyzer.service;

import com.currency.currency_analyzer.model.Settings;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SettingsService {

        private static final String
DATA_PATH = "/var/data/";

    private static final String
            FILE_NAME =
            "settings.json";

    private final ObjectMapper
            objectMapper =
            new ObjectMapper();

    public Settings getSettings() {

        try {

            File file =
                    new File( DATA_PATH
        + "settings.json");

            if (!file.exists()) {

                Settings settings =
                        new Settings();

                settings.setBaseCurrency(
                        "EUR"
                );

                settings.setSelectedCurrencies(
                        "USD,CZK,GBP"
                );

                saveSettings(settings);

                return settings;
            }

            return objectMapper.readValue(
                    file,
                    Settings.class
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load settings"
            );
        }
    }

    public void saveSettings(
            Settings settings
    ) {

        try {

            objectMapper.writeValue(
                    new File( DATA_PATH
        + "settings.json"),
                    settings
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to save settings"
            );
        }
    }
}