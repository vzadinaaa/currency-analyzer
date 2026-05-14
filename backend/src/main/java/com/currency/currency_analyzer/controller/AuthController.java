package com.currency.currency_analyzer.controller;

import com.currency.currency_analyzer.model.LoginRequest;
import com.currency.currency_analyzer.service.AuthService;
import com.currency.currency_analyzer.service.AuthTokenService;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")

@RestController
public class AuthController {

    private final AuthService
            authService;

    private final AuthTokenService
        authTokenService;

    public AuthController(

        AuthService authService,

        AuthTokenService authTokenService
) {

        this.authService =
                authService;

        this.authTokenService =
        authTokenService;
    }

    @PostMapping("/api/login")
    public Map<String, Object> login(

            @RequestBody
            LoginRequest request
    ) {

        boolean success =

                authService.login(

                        request.getUsername(),

                        request.getPassword()
                );

        if (success) {

    return Map.of(

            "success",
            true,

            "token",
            authTokenService.getToken()
    );
}

return Map.of(
        "success",
        false
);
    }
}