package com.wpshop.controller;

import com.wpshop.model.Price;
import org.springframework.hateoas.ResourceSupport;

public class OfferDTO extends ResourceSupport {

    private long offerId;
    private String name;
    private String description;
    private Price price;

    OfferDTO(String name, String description, Price price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    OfferDTO() {}

    public void setOfferId(long id) {
        this. offerId = id;
    }

    public long getOfferId() {
        return offerId;
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
}