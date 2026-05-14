package com.currency.currency_analyzer.controller;

import com.currency.currency_analyzer.model.Settings;
import com.currency.currency_analyzer.service.SettingsService;
import com.currency.currency_analyzer.service.AuthTokenService;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://currency-analyzer-theta.vercel.app/")

@RestController
public class SettingsController {

    private final SettingsService
            settingsService;

    private final AuthTokenService
        authTokenService;

    public SettingsController(

        SettingsService settingsService,

        AuthTokenService authTokenService
) {

        this.settingsService =
                settingsService;

        this.authTokenService =
        authTokenService;
    }

    @GetMapping("/api/settings")
public Settings getSettings(

        @RequestHeader("Authorization")
        String token
) {

    if (
            !authTokenService
                    .validateToken(token)
    ) {

        throw new RuntimeException(
                "Unauthorized"
        );
    }

    return settingsService
            .getSettings();
}

    @PostMapping("/api/settings")
public void saveSettings(

        @RequestHeader("Authorization")
        String token,

        @RequestBody
        Settings settings
) {

    if (
            !authTokenService
                    .validateToken(token)
    ) {

        throw new RuntimeException(
                "Unauthorized"
        );
    }

    settingsService
            .saveSettings(settings);
}
}