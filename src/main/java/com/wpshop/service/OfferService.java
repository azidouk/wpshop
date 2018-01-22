package com.wpshop.service;

import com.wpshop.model.Offer;
import com.wpshop.model.Price;
import com.wpshop.model.Store;
import com.wpshop.utils.RepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class OfferService implements RepositoryManager {

    @Autowired
    private Store store;

    public Offer createOffer(String name, String description, Price price) {
        return store.createOffer(name, description, price);
    }

    public List<Offer> getAllOffers() {
        return store.getAllOffers();
    }

    public Optional<Offer> getOfferById(long id) {
        return store.getOfferById(id);
    }

    void clear() {
        store.clear();
    }
}
