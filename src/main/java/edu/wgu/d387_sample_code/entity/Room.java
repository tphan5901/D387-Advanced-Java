package edu.wgu.d387_sample_code.entity;

public class Room {
    private final double priceUSD;
    private final double priceCAD;
    private final double priceEUR;

    public Room(double priceUSD) {
        this.priceUSD = priceUSD;
        this.priceCAD = CurrencyConverter.convert("CAD", priceUSD);
        this.priceEUR = CurrencyConverter.convert("EUR", priceUSD);
    }

    public double getPriceUSD() {
        return priceUSD;
    }

    public double getPriceCAD() {
        return priceCAD;
    }

    public double getPriceEUR() {
        return priceEUR;
    }
}