package com.wpshop.utils;

import com.wpshop.model.Offer;
import com.wpshop.model.Price;

import java.util.List;

public interface RepositoryManager {

    List<Offer> getAllOffers();

    Offer createOffer(String name, String description, Price price);

}
