package com.currency.currency_analyzer.service;

import com.currency.currency_analyzer.model.AnalysisResponse;
import com.currency.currency_analyzer.model.ExchangeRateResponse;
import com.currency.currency_analyzer.model.HistoricalRatesResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class ExchangeRateService {

    private HistoricalRatesResponse
            cachedHistoricalResponse;

    private String cachedHistoricalDate;

    private final CalculationLogService
            calculationLogService;

    @Value("${exchange.api.key}")
    private String apiKey;

    private final LogService
            logService;

    private final ObjectMapper
            objectMapper =
            new ObjectMapper();

    public ExchangeRateService(

            LogService logService,

            CalculationLogService calculationLogService
    ) {

        this.logService =
                logService;

        this.calculationLogService =
                calculationLogService;
    }

    public ExchangeRateResponse
    getLatestRates() {

        String url =
                "https://api.exchangerate.host/live"
                + "?access_key=" + apiKey
                + "&source=EUR"
                + "&currencies=USD,CZK,GBP";

        RestTemplate restTemplate =
                new RestTemplate();

        try {

            return restTemplate.getForObject(
                    url,
                    ExchangeRateResponse.class
            );

        } catch (Exception e) {

            logService.logError(
                    "Failed to fetch latest rates"
            );

            throw e;
        }
    }

    public AnalysisResponse analyzeRates(

            String base,

            String symbols
    ) throws Exception {

        String url =
                "https://api.exchangerate.host/live"
                + "?access_key=" + apiKey
                + "&source=" + base
                + "&currencies=" + symbols;

        RestTemplate restTemplate =
                new RestTemplate();

        ExchangeRateResponse response;

        boolean fromCache = false;

        try {

            response =
                    restTemplate.getForObject(
                            url,
                            ExchangeRateResponse.class
                    );

            objectMapper.writeValue(

                    new File(
                            "cached_rates.json"
                    ),

                    response
            );

        } catch (Exception e) {

            logService.logError(
                    "Failed to fetch analysis data"
            );

            try {

                response =
                        objectMapper.readValue(

                                new File(
                                        "cached_rates.json"
                                ),

                                ExchangeRateResponse.class
                        );

                fromCache = true;

            } catch (Exception ex) {

                throw new RuntimeException(
                        "No cached data available"
                );
            }
        }

        if (
                response == null
                ||
                response.getQuotes() == null
        ) {

            throw new RuntimeException(
                    "API did not return quotes"
            );
        }

        AnalysisResponse analysis =

                createAnalysisResponse(

                        response.getQuotes(),

                        symbols,

                        base
                );

        analysis.setFromCache(
                fromCache
        );

        analysis.setCacheDate(
                LocalDateTime.now()
                        .toString()
        );

        return analysis;
    }

    public AnalysisResponse
    analyzeHistoricalRates(

            String base,

            String symbols,

            String date
    ) {

        String url =
                "https://api.exchangerate.host/historical"
                + "?access_key=" + apiKey
                + "&date=" + date
                + "&source=" + base;

        RestTemplate restTemplate =
                new RestTemplate();

        Map response;

        boolean fromCache = false;

        try {

            HttpHeaders headers =
                    new HttpHeaders();

            headers.set(
                    "User-Agent",
                    "Mozilla/5.0"
            );

            HttpEntity<String> entity =
                    new HttpEntity<>(headers);

            ResponseEntity<Map>
                    responseEntity =

                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            Map.class
                    );

            response =
                    responseEntity.getBody();

        objectMapper.writeValue(

        new File(
                "cached_historical_rates.json"
        ),

        response
);

        } catch (Exception e) {

            logService.logError(
                    "Failed to fetch historical analysis"
            );

            try {

    response =
            objectMapper.readValue(

                    new File(
                            "cached_historical_rates.json"
                    ),

                    Map.class
            );

    fromCache = true;

} catch (Exception ex) {

    throw new RuntimeException(
            "No cached historical data available"
    );
}
        }

        if (response == null) {

            throw new RuntimeException(
                    "API did not return data"
            );
        }

        Map<String, Double> quotes =

                (Map<String, Double>)
                        response.get("quotes");

        if (quotes == null) {

            throw new RuntimeException(
                    "API did not return quotes"
            );
        }

        AnalysisResponse analysis =

        createAnalysisResponse(

                quotes,

                symbols,

                base
        );

analysis.setFromCache(
        fromCache
);

analysis.setCacheDate(
        LocalDateTime.now()
                .toString()
);

return analysis;
    }

    private AnalysisResponse
    createAnalysisResponse(

            Map<String, Double> rates,

            String symbols,

            String base
    ) {

        AnalysisResponse analysis =
                new AnalysisResponse();

        String strongestCurrency =
                "";

        double strongestValue =
                Double.MIN_VALUE;

        String weakestCurrency =
                "";

        double weakestValue =
                Double.MAX_VALUE;

        double sum = 0;

        int count = 0;

        Map<String, Double> cleanRates =
                new HashMap<>();

        String[] selectedCurrencies =

                symbols.replace(
                        " ",
                        ""
                ).split(",");

        for (
                String currency :
                selectedCurrencies
        ) {

            currency = currency.trim();

            if (
                    currency.isEmpty()
            ) {

                continue;
            }

            String key =
                    base + currency;

            double value = 0;

            if (
                    currency.equals(base)
            ) {

                value = 1.0;

            } else if (
                    rates.containsKey(currency)
            ) {

                value =
                        rates.get(currency);

            } else if (
                    rates.containsKey(key)
            ) {

                value =
                        rates.get(key);
            }

            cleanRates.put(
                    currency,
                    value
            );

            if (
                    value > strongestValue
            ) {

                strongestValue =
                        value;

                strongestCurrency =
                        currency;
            }

            if (
                    value < weakestValue
            ) {

                weakestValue =
                        value;

                weakestCurrency =
                        currency;
            }

            sum += value;

            count++;
        }

        double average =
                sum / count;

        analysis.setRates(
                cleanRates
        );

        analysis.setStrongestCurrency(
                strongestCurrency
        );

        analysis.setStrongestValue(
                strongestValue
        );

        analysis.setWeakestCurrency(
                weakestCurrency
        );

        analysis.setWeakestValue(
                weakestValue
        );

        analysis.setAverage(
                average
        );

        calculationLogService
                .saveCalculation(

                        base,

                        symbols,

                        strongestCurrency,

                        weakestCurrency,

                        average
                );

        return analysis;
    }

    public HistoricalRatesResponse
    getHistoricalRates(

            String base,

            String symbol,

            String date
    ) {

        if (
                cachedHistoricalResponse != null
                &&
                date.equals(
                        cachedHistoricalDate
                )
        ) {

            return cachedHistoricalResponse;
        }

        String url =
                "https://api.exchangerate.host/historical"
                + "?access_key=" + apiKey
                + "&date=" + date
                + "&source=" + base;

        RestTemplate restTemplate =
                new RestTemplate();

        Map response;

        try {

            HttpHeaders headers =
                    new HttpHeaders();

            headers.set(
                    "User-Agent",
                    "Mozilla/5.0"
            );

            HttpEntity<String> entity =
                    new HttpEntity<>(headers);

            ResponseEntity<Map>
                    responseEntity =

                    restTemplate.exchange(
                            url,
                            HttpMethod.GET,
                            entity,
                            Map.class
                    );

            response =
                    responseEntity.getBody();

        } catch (Exception e) {

            logService.logError(
                    "Failed to fetch historical data"
            );

            throw e;
        }

        if (response == null) {

            throw new RuntimeException(
                    "API did not return data"
            );
        }

        Map<String, Double> quotes =

                (Map<String, Double>)
                        response.get("quotes");

        if (quotes == null) {

            throw new RuntimeException(
                    "API did not return quotes"
            );
        }

        Map<String, Map<String, Double>>
                rates = new HashMap<>();

        Map<String, Double> dailyRates =
                new HashMap<>();

        String[] currencyList =
                symbol.split(",");

        for (
                String currency :
                currencyList
        ) {

            String apiKeyName =
                    base + currency;

            if (
                    currency.equals(base)
            ) {

                dailyRates.put(
                        currency,
                        1.0
                );

            } else if (
                    quotes.containsKey(
                            apiKeyName
                    )
            ) {

                dailyRates.put(
                        currency,
                        quotes.get(
                                apiKeyName
                        )
                );

            } else {

                dailyRates.put(
                        currency,
                        0.0
                );
            }
        }

        rates.put(
                date,
                dailyRates
        );

        HistoricalRatesResponse result =
                new HistoricalRatesResponse();

        result.setRates(
                rates
        );

        cachedHistoricalResponse =
                result;

        cachedHistoricalDate =
                date;

        return result;
    }
}