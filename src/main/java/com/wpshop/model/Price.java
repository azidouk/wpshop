package com.wpshop.model;

import java.util.Objects;

public final class Price {

    private float price;
    private Currency currency;

    public Price(float price, Currency currency) {
        this.price = price;
        this.currency = currency;
    }

    protected Price() {}

    public float getPrice(){
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Float.compare(price1.price, price) == 0 &&
                currency == price1.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, currency);
    }
}
