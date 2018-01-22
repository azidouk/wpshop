package com.wpshop.model;

import com.wpshop.utils.RepositoryManager;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class Store implements RepositoryManager {

    private Map<Long, Offer> offersById;

    public Store() {
        offersById = new ConcurrentHashMap<>();
    }

    public Offer createOffer(String name, String description, Price price) {
        Offer offer = new Offer(name, description, price);
        offersById.put(offer.getId(), offer);
        return offer;
    }

    public List<Offer> getAllOffers() {
        return new ArrayList<>(offersById.values());
    }

    public Optional<Offer> getOfferById(long id) {
        return Optional.ofNullable(offersById.get(id));
    }

    public void clear() {
        offersById.clear();
        IdGenerator.reset();
    }

    public int size() {
        return offersById.size();
    }
}