package com.wpshop.model;

public class Offer implements org.springframework.hateoas.Identifiable{

    private long id;
    private String name;
    private String description;
    private Price price;

    protected Offer() {}

    public Offer(String name, String description, float price, Currency currency)
    {
        this.name = name;
        this.description = description;
        this.price = new Price(price, currency);
    }

    public Long getId () {
        return id;
    }

    public void setId() {
        this.id = IdGenerator.getNextId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}