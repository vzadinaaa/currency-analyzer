package com.currency.currency_analyzer.controller;

import com.currency.currency_analyzer.model.LoginRequest;

import com.currency.currency_analyzer.service.AuthService;
import com.currency.currency_analyzer.service.AuthTokenService;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AuthControllerTest {

    @Test
    void testLoginSuccess() {

        AuthService authService =
                new AuthService();

        AuthTokenService authTokenService =
                new AuthTokenService();

        AuthController controller =
                new AuthController(

                        authService,

                        authTokenService
                );

        LoginRequest request =
                new LoginRequest();

        request.setUsername(
                "admin"
        );

        request.setPassword(
                "admin123"
        );

        Map<String, Object> response =
                controller.login(request);

        assertNotNull(response);
    }

    @Test
    void testLoginFail() {

        AuthService authService =
                new AuthService();

        AuthTokenService authTokenService =
                new AuthTokenService();

        AuthController controller =
                new AuthController(

                        authService,

                        authTokenService
                );

        LoginRequest request =
                new LoginRequest();

        request.setUsername(
                "wrong"
        );

        request.setPassword(
                "wrong"
        );

        Map<String, Object> response =
                controller.login(request);

        assertNotNull(response);
    }
}