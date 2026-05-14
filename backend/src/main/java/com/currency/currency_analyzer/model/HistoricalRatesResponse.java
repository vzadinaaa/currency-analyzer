package com.currency.currency_analyzer.model;

import java.util.Map;

public class HistoricalRatesResponse {

    private Map<String, Map<String, Double>> rates;

    public Map<String, Map<String, Double>> getRates() {
        return rates;
    }

    public void setRates(Map<String, Map<String, Double>> rates) {
        this.rates = rates;
    }
}