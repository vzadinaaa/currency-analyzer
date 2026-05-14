package com.currency.currency_analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyAnalyzerApplication {

	private static final String
DATA_PATH = "/var/data/";

	public static void main(String[] args) {
		SpringApplication.run(CurrencyAnalyzerApplication.class, args);
	}

}
