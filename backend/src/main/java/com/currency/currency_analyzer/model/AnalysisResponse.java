package com.currency.currency_analyzer.model;

import java.util.Map;

public class AnalysisResponse {

    private String strongestCurrency;

    private double strongestValue;

    private String weakestCurrency;

    private double weakestValue;

    private double average;

    private Map<String, Double> rates;

    private boolean fromCache;

private String cacheDate;

    public String getStrongestCurrency() {

        return strongestCurrency;
    }

    public void setStrongestCurrency(
            String strongestCurrency
    ) {

        this.strongestCurrency =
                strongestCurrency;
    }

    public double getStrongestValue() {

        return strongestValue;
    }

    public void setStrongestValue(
            double strongestValue
    ) {

        this.strongestValue =
                strongestValue;
    }

    public String getWeakestCurrency() {

        return weakestCurrency;
    }

    public void setWeakestCurrency(
            String weakestCurrency
    ) {

        this.weakestCurrency =
                weakestCurrency;
    }

    public double getWeakestValue() {

        return weakestValue;
    }

    public void setWeakestValue(
            double weakestValue
    ) {

        this.weakestValue =
                weakestValue;
    }

    public double getAverage() {

        return average;
    }

    public void setAverage(
            double average
    ) {

        this.average = average;
    }

    public Map<String, Double> getRates() {

        return rates;
    }

    public void setRates(
            Map<String, Double> rates
    ) {

        this.rates = rates;
    }

    public boolean isFromCache() {

    return fromCache;
}

public void setFromCache(
        boolean fromCache
) {

    this.fromCache = fromCache;
}

public String getCacheDate() {

    return cacheDate;
}

public void setCacheDate(
        String cacheDate
) {

    this.cacheDate = cacheDate;
}
}