package com.wpshop.model;

 public enum Currency {
    USD("USD"),
    GBP("GBP"),
    EUR("EUR");

    private String name;

    Currency(String currency) {
        this.name = currency;
    }

    public String getName(){
        return this.name;
    }
}
