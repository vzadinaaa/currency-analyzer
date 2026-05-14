package com.currency.currency_analyzer.service;

import org.springframework.stereotype.Service;

@Service
public class AuthTokenService {

    private static final String
DATA_PATH =

        System.getProperty("os.name")
                .toLowerCase()
                .contains("win")

        ?

        "C:/var/data/"

        :

        "/var/data/";

    private static final String TOKEN =
            "currency-analyzer-token";

    public String getToken() {

        return TOKEN;
    }

    public boolean validateToken(
            String token
    ) {

        return TOKEN.equals(token);
    }
}