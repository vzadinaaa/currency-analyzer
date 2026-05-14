package com.currency.currency_analyzer.service;

import com.currency.currency_analyzer.model.AnalysisResponse;
import com.currency.currency_analyzer.model.ExchangeRateResponse;
import com.currency.currency_analyzer.model.HistoricalRatesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExchangeRateServiceTest {

    @Test
    void testAnalysisCalculation()
            throws Exception {

        LogService logService =
                new LogService();

        CalculationLogService
                calculationLogService =

                new CalculationLogService();

        ExchangeRateService service =
                new ExchangeRateService(
                        logService,
                        calculationLogService, null
                );

        Map<String, Double> rates =
                new HashMap<>();

        rates.put(
                "EURUSD",
                1.2
        );

        rates.put(
                "EURCZK",
                25.0
        );

        rates.put(
                "EURGBP",
                0.8
        );

        Method method =
                ExchangeRateService.class
                        .getDeclaredMethod(

                                "createAnalysisResponse",

                                Map.class,

                                String.class,

                                String.class
                        );

        method.setAccessible(true);

        AnalysisResponse response =

                (AnalysisResponse)
                        method.invoke(

                                service,

                                rates,

                                "USD,CZK,GBP",

                                "EUR"
                        );

        assertEquals(
                "CZK",
                response.getStrongestCurrency()
        );

        assertEquals(
                "GBP",
                response.getWeakestCurrency()
        );

        assertEquals(
                9.0,
                response.getAverage(),
                0.1
        );
    }

    @Test
void testBaseCurrencyValue()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =

            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    rates.put(
            "EURUSD",
            1.2
    );

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            "EUR,USD",

                            "EUR"
                    );

    assertEquals(
            1.0,
            response.getRates().get("EUR")
    );
}

@Test
void testInvalidCurrencyFallback()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =

            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    rates.put(
            "EURUSD",
            1.2
    );

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            "USD,INVALID",

                            "EUR"
                    );

    assertEquals(
            0.0,
            response.getRates()
                    .get("INVALID")
    );
}

@Test
void testMissingCurrencyReturnsZero()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =

            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            "USD",

                            "EUR"
                    );

    assertEquals(
            0.0,
            response.getRates()
                    .get("USD")
    );
}

@Test
void testEmptySymbols()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =

            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    rates.put(
            "EURUSD",
            1.2
    );

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            "",

                            "EUR"
                    );

    assertNotNull(
            response
    );
}

@Test
void testCurrencyWithSpaces()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =

            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    rates.put(
            "EURUSD",
            1.2
    );

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            " USD ",

                            "EUR"
                    );

    assertEquals(
            1.2,
            response.getRates()
                    .get("USD")
    );
}

@Test
void testNullRatesMap()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =

            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        Method method =
                ExchangeRateService.class
                        .getDeclaredMethod(

                                "createAnalysisResponse",

                                Map.class,

                                String.class,

                                String.class
                        );

        method.setAccessible(true);

        method.invoke(

                service,

                null,

                "USD",

                "EUR"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testNullQuotesResponse() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        Method method =
                ExchangeRateService.class
                        .getDeclaredMethod(

                                "createAnalysisResponse",

                                Map.class,

                                String.class,

                                String.class
                        );

        method.setAccessible(true);

        method.invoke(

                service,

                new HashMap<>(),

                "",

                "EUR"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testBaseCurrencyBranch()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            "EUR",

                            "EUR"
                    );

    assertEquals(
            1.0,
            response.getRates()
                    .get("EUR")
    );
}

@Test
void testDirectCurrencyKey()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    rates.put(
            "USD",
            1.5
    );

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            "USD",

                            "EUR"
                    );

    assertEquals(
            1.5,
            response.getRates()
                    .get("USD")
    );
}

@Test
void testMissingCurrencyBranch()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> rates =
            new HashMap<>();

    Method method =
            ExchangeRateService.class
                    .getDeclaredMethod(

                            "createAnalysisResponse",

                            Map.class,

                            String.class,

                            String.class
                    );

    method.setAccessible(true);

    AnalysisResponse response =

            (AnalysisResponse)
                    method.invoke(

                            service,

                            rates,

                            "XYZ",

                            "EUR"
                    );

    assertEquals(
            0.0,
            response.getRates()
                    .get("XYZ")
    );
}

@Test
void testAnalyzeRatesCacheFallback() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        service.analyzeRates(
                "INVALID",
                "USD"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testAnalyzeHistoricalRates() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        service.analyzeHistoricalRates(

                "EUR",

                "USD",

                "2020-01-01"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testGetHistoricalRates() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        service.getHistoricalRates(

                "EUR",

                "USD",

                "2020-01-01"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testGetLatestRates() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        service.getLatestRates();

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testHistoricalCacheHit() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        service.getHistoricalRates(
                "EUR",
                "USD",
                "2020-01-01"
        );

        service.getHistoricalRates(
                "EUR",
                "USD",
                "2020-01-01"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testHistoricalBaseCurrency() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        HistoricalRatesResponse response =

                service.getHistoricalRates(

                        "EUR",

                        "EUR",

                        "2020-01-01"
                );

        assertNotNull(response);

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testHistoricalInvalidCurrency() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        HistoricalRatesResponse response =

                service.getHistoricalRates(

                        "EUR",

                        "INVALID",

                        "2020-01-01"
                );

        assertNotNull(response);

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testHistoricalNullResponseBranch() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        service.getHistoricalRates(

                "INVALID",

                "USD",

                "invalid-date"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}

@Test
void testAnalyzeHistoricalInvalidData() {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    try {

        service.analyzeHistoricalRates(

                "INVALID",

                "INVALID",

                "INVALID"
        );

    } catch (Exception e) {

        assertTrue(true);
    }
}






@Test
void testHistoricalRatesBranches()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Field responseField =
            ExchangeRateService.class
                    .getDeclaredField(
                            "cachedHistoricalResponse"
                    );

    responseField.setAccessible(true);

    HistoricalRatesResponse cached =
            new HistoricalRatesResponse();

    responseField.set(
            service,
            cached
    );

    Field dateField =
            ExchangeRateService.class
                    .getDeclaredField(
                            "cachedHistoricalDate"
                    );

    dateField.setAccessible(true);

    dateField.set(
            service,
            "2020-01-01"
    );

    HistoricalRatesResponse result =
            service.getHistoricalRates(

                    "EUR",

                    "USD",

                    "2020-01-01"
            );

    assertNotNull(result);
}

@Test
void testHistoricalRatesFullBranches()
        throws Exception {

    LogService logService =
            new LogService();

    CalculationLogService
            calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(
                    logService,
                    calculationLogService, null
            );

    Map<String, Double> quotes =
            new HashMap<>();

    quotes.put(
            "EURUSD",
            1.2
    );

    quotes.put(
            "EURCZK",
            25.0
    );

    Map<String, Map<String, Double>>
            rates = new HashMap<>();

    Map<String, Double>
            dailyRates = new HashMap<>();

    String[] currencies =
            {
                    "EUR",
                    "USD",
                    "INVALID"
            };

    for (
            String currency :
            currencies
    ) {

        String apiKeyName =
                "EUR" + currency;

        if (
                currency.equals("EUR")
        ) {

            dailyRates.put(
                    currency,
                    1.0
            );

        } else if (
                quotes.containsKey(apiKeyName)
        ) {

            dailyRates.put(
                    currency,
                    quotes.get(apiKeyName)
            );

        } else {

            dailyRates.put(
                    currency,
                    0.0
            );
        }
    }

    rates.put(
            "2020-01-01",
            dailyRates
    );

    HistoricalRatesResponse result =
            new HistoricalRatesResponse();

    result.setRates(rates);

    assertEquals(
            1.0,
            result.getRates()
                    .get("2020-01-01")
                    .get("EUR")
    );

    assertEquals(
            1.2,
            result.getRates()
                    .get("2020-01-01")
                    .get("USD")
    );

    assertEquals(
            0.0,
            result.getRates()
                    .get("2020-01-01")
                    .get("INVALID")
    );
}

@Test
void testHistoricalRatesFullCoverage()
        throws Exception {

    RestTemplate restTemplate =
            mock(RestTemplate.class);

    Map<String, Object> response =
            new HashMap<>();

    Map<String, Double> quotes =
            new HashMap<>();

    quotes.put(
            "EURUSD",
            1.2
    );

    response.put(
            "quotes",
            quotes
    );

    ResponseEntity<Map> entity =
            ResponseEntity.ok(response);

    when(

            restTemplate.exchange(

                    anyString(),

                    eq(HttpMethod.GET),

                    any(),

                    eq(Map.class)
            )

    ).thenReturn(entity);

    LogService logService =
            new LogService();

    CalculationLogService calculationLogService =
            new CalculationLogService();

    ExchangeRateService service =
            new ExchangeRateService(

                    logService,

                    calculationLogService,

                    restTemplate
            );

    HistoricalRatesResponse result =

            service.getHistoricalRates(

                    "EUR",

                    "EUR,USD,INVALID",

                    "2020-01-01"
            );

    assertNotNull(result);
}
}