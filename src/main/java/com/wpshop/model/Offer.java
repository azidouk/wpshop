package com.wpshop.model;

import java.util.Objects;

public final class Offer {

    private final long id;
    private final String name;
    private final String description;
    private final Price price;

    public Offer(String name, String description, Price price) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.id = IdGenerator.getNextId();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id == offer.id &&
                Objects.equals(name, offer.name) &&
                Objects.equals(description, offer.description) &&
                Objects.equals(price, offer.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price);
    }
}