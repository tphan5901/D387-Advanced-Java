package edu.wgu.d387_sample_code.entity;

public class CurrencyConverter {
    public static double convert(String currency, double priceInUSD) {
        switch (currency) {
            case "CAD":
                return priceInUSD * 1.25;
            case "EUR":
                return priceInUSD * 0.85;
            default:
                return priceInUSD;
        }
    }
}
