package com.currency.currency_analyzer.model;

public class Settings {

    private String baseCurrency;

    private String selectedCurrencies;

    private String language;

    public String getBaseCurrency() {

        return baseCurrency;
    }

    public void setBaseCurrency(
            String baseCurrency
    ) {

        this.baseCurrency =
                baseCurrency;
    }

    public String getSelectedCurrencies() {

        return selectedCurrencies;
    }

    public String getLanguage() {

    return language;
}

    public void setSelectedCurrencies(
            String selectedCurrencies
    ) {

        this.selectedCurrencies =
                selectedCurrencies;
    }

    public void setLanguage(
        String language
) {

    this.language = language;
}
}