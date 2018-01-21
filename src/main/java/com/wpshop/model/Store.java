package com.wpshop.model;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class Store {

    private Map<Long, Offer> elements;

    public Store() {
        elements = new HashMap<>();
    }

    public Offer createOffer(Offer element) {
        element.setId();
        elements.put(element.getId(), element);
        return element;
    }

    public List<Offer> findAllOffers() {
        return new ArrayList<>(elements.values());
    }

    public Optional<Offer> findOfferById(long id) {
        return Optional.ofNullable(elements.get(id));
    }

    public void clear() {
        elements.clear();
        IdGenerator.reset();
    }

    public int size() {
        return elements.size();
    }
}