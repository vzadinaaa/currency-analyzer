package com.currency.currency_analyzer.model;

import java.util.Map;

public class ExchangeRateResponse {

    private double amount;
    private String base;
    private String date;
    private Map<String, Double> quotes;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getQuotes() {
    return quotes;
}

    public void setQuotes(
        Map<String, Double> quotes
) {
    this.quotes = quotes;
}
}