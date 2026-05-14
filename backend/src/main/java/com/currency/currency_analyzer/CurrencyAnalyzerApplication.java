package com.currency.currency_analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyAnalyzerApplication {

	private static final String
DATA_PATH =

        System.getProperty("os.name")
                .toLowerCase()
                .contains("win")

        ?

        "C:/var/data/"

        :

        "/var/data/";

	public static void main(String[] args) {
		SpringApplication.run(CurrencyAnalyzerApplication.class, args);
	}

}
