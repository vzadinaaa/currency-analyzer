package com.currency.currency_analyzer.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenServiceTest {

    @Test
    void testValidToken() {

        AuthTokenService service =
                new AuthTokenService();

        String token =
                service.getToken();

        assertTrue(
                service.validateToken(token)
        );
    }

    @Test
    void testInvalidToken() {

        AuthTokenService service =
                new AuthTokenService();

        assertFalse(
                service.validateToken(
                        "invalid-token"
                )
        );
    }
}