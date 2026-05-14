package com.currency.currency_analyzer.service;

import com.currency.currency_analyzer.model.Settings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class SettingsServiceTest {

    @Test
    void testSaveAndLoadSettings()
            throws Exception {

        SettingsService service =
                new SettingsService();

        Settings settings =
                new Settings();

        settings.setBaseCurrency(
                "EUR"
        );

        settings.setSelectedCurrencies(
                "USD,CZK,GBP"
        );

        settings.setLanguage(
                "en"
        );

        service.saveSettings(
                settings
        );

        Settings loaded =
                service.getSettings();

        assertEquals(
                "EUR",
                loaded.getBaseCurrency()
        );

        assertEquals(
                "USD,CZK,GBP",
                loaded.getSelectedCurrencies()
        );

        assertEquals(
                "en",
                loaded.getLanguage()
        );
    }

@Test
void testGetSettingsWithoutFile() {

    File file =
            new File(
                    "settings.json"
            );

    if (file.exists()) {

        file.delete();
    }

    SettingsService service =
            new SettingsService();

    Settings settings =
            service.getSettings();

    assertNotNull(
            settings
    );
}


}