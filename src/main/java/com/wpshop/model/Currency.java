package com.wpshop.model;

 public enum Currency {
    USD("USD"),
    GBP("GBP"),
    EUR("EUR");

    private final String name;

    Currency(String currency) {
        this.name = currency;
    }

    public String getName(){
        return this.name;
    }
}
