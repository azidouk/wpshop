package com.wpshop.model;

public class Price {

    private float price;
    private Currency currency;

    protected Price(){}

    public Price(float price, Currency currency) {
        this.price = price;
        this.currency = currency;
    }

    public float getPrice(){
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }
}
