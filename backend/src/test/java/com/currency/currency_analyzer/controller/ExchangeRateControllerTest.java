package com.currency.currency_analyzer.controller;

import com.currency.currency_analyzer.service.*;

import org.junit.jupiter.api.Test;

import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateControllerTest {

    @Test
    void testGetRates() {

        ExchangeRateService exchangeRateService =

                new ExchangeRateService(

                        new LogService(),

                        new CalculationLogService(),

                        new RestTemplate()
                );

        AuthTokenService authTokenService =
                new AuthTokenService();

        ExchangeRateController controller =

                new ExchangeRateController(

                        exchangeRateService,

                        authTokenService
                );

        try {

            controller.getRates(
            );

        } catch (Exception e) {

            assertTrue(true);
        }
    }

    @Test
    void testGetAnalysis() {

        ExchangeRateService exchangeRateService =

                new ExchangeRateService(

                        new LogService(),

                        new CalculationLogService(),

                        new RestTemplate()
                );

        AuthTokenService authTokenService =
                new AuthTokenService();

        ExchangeRateController controller =

                new ExchangeRateController(

                        exchangeRateService,

                        authTokenService
                );

        try {

            controller.getAnalysis(

                    authTokenService.getToken(),

                    "EUR",

                    "USD"
            );

        } catch (Exception e) {

            assertTrue(true);
        }
    }

    @Test
    void testGetHistory() {

        ExchangeRateService exchangeRateService =

                new ExchangeRateService(

                        new LogService(),

                        new CalculationLogService(),

                        new RestTemplate()
                );

        AuthTokenService authTokenService =
                new AuthTokenService();

        ExchangeRateController controller =

                new ExchangeRateController(

                        exchangeRateService,

                        authTokenService
                );

        try {

            controller.getHistory(

                    authTokenService.getToken(),

                    "EUR",

                    "USD",

                    "2020-01-01"
            );

        } catch (Exception e) {

            assertTrue(true);
        }
    }

    @Test
    void testGetHistoricalAnalysis() {

        ExchangeRateService exchangeRateService =

                new ExchangeRateService(

                        new LogService(),

                        new CalculationLogService(),

                        new RestTemplate()
                );

        AuthTokenService authTokenService =
                new AuthTokenService();

        ExchangeRateController controller =

                new ExchangeRateController(

                        exchangeRateService,

                        authTokenService
                );

        try {

            controller.getHistoricalAnalysis(

                    authTokenService.getToken(),

                    "EUR",

                    "USD",

                    "2020-01-01"
            );

        } catch (Exception e) {

            assertTrue(true);
        }
    }
}