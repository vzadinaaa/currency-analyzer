package com.currency.currency_analyzer.controller;

import com.currency.currency_analyzer.model.Settings;

import com.currency.currency_analyzer.service.AuthTokenService;
import com.currency.currency_analyzer.service.SettingsService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsControllerTest {

    @Test
    void testGetSettings() {

        SettingsService settingsService =
                new SettingsService();

        AuthTokenService authTokenService =
                new AuthTokenService();

        SettingsController controller =
                new SettingsController(

                        settingsService,

                        authTokenService
                );

        Settings settings =
                controller.getSettings(
                        authTokenService.getToken()
                );

        assertNotNull(settings);
    }
}