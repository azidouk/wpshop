package com.wpshop.service;

import com.wpshop.model.Offer;
import com.wpshop.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    @Autowired
    private Store store;

    public Offer createOffer(Offer offer) {
        return store.createOffer(offer);
    }

    public List<Offer> findAllOffers() {
        return store.findAllOffers();
    }

    public Optional<Offer> findOfferById(long id) {
        return store.findOfferById(id);
    }

    void clear() {
        store.clear();
    }
}
