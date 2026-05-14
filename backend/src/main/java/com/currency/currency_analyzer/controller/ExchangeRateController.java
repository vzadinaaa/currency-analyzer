package com.currency.currency_analyzer.controller;

import com.currency.currency_analyzer.model.AnalysisResponse;
import com.currency.currency_analyzer.model.ExchangeRateResponse;
import com.currency.currency_analyzer.model.HistoricalRatesResponse;
import com.currency.currency_analyzer.service.ExchangeRateService;
import com.currency.currency_analyzer.service.AuthTokenService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "https://currency-analyzer-theta.vercel.app/")

@RestController
public class ExchangeRateController {

    private final ExchangeRateService
            exchangeRateService;

    private final AuthTokenService
        authTokenService;

    public ExchangeRateController(

        ExchangeRateService exchangeRateService,

        AuthTokenService authTokenService
) {

        this.exchangeRateService =
                exchangeRateService;

        this.authTokenService =
        authTokenService;
    }

    @GetMapping("/api/rates")
    public ExchangeRateResponse getRates() {

        

        return exchangeRateService
                .getLatestRates();
    }

    @GetMapping("/api/analysis")
public AnalysisResponse getAnalysis(

        @RequestHeader("Authorization")
        String token,

        @RequestParam String base,

        @RequestParam String symbols
) throws Exception {

    if (
            !authTokenService
                    .validateToken(token)
    ) {

        throw new RuntimeException(
                "Unauthorized"
        );
    }

    return exchangeRateService
            .analyzeRates(
                    base,
                    symbols
            );
}
    @GetMapping("/api/analysis/historical")
public AnalysisResponse
getHistoricalAnalysis(

        @RequestHeader("Authorization")
        String token,

        @RequestParam String base,

        @RequestParam String symbols,

        @RequestParam String date
) {

    if (
            !authTokenService
                    .validateToken(token)
    ) {

        throw new RuntimeException(
                "Unauthorized"
        );
    }

    return exchangeRateService
            .analyzeHistoricalRates(
                    base,
                    symbols,
                    date
            );
}

    @GetMapping("/api/history")
public HistoricalRatesResponse getHistory(

        @RequestHeader("Authorization")
        String token,

        @RequestParam String base,

        @RequestParam String symbol,

        @RequestParam String date
) {

    if (
            !authTokenService
                    .validateToken(token)
    ) {

        throw new RuntimeException(
                "Unauthorized"
        );
    }

    return exchangeRateService
            .getHistoricalRates(
                    base,
                    symbol,
                    date
            );
}
}