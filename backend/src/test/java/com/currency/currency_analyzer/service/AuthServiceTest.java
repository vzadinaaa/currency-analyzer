package com.currency.currency_analyzer.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Test
    void testHashPassword() {

        AuthService service =
                new AuthService();

        String hashed =
                service.hashPassword(
                        "admin123"
                );

        assertNotNull(
                hashed
        );

        assertNotEquals(
                "admin123",
                hashed
        );
    }

    @Test
    void testLoginInvalidUser() {

        AuthService service =
                new AuthService();

        boolean result =
                service.login(

                        "wrong",

                        "wrong"
                );

        assertFalse(
                result
        );
    }

    @Test
    void testLoginEmptyValues() {

        AuthService service =
                new AuthService();

        boolean result =
                service.login(
                        "",
                        ""
                );

        assertFalse(
                result
        );
    }
}