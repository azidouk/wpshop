package com.wpshop.utils;

import com.wpshop.model.Currency;
import com.wpshop.model.Offer;

import static org.junit.Assert.assertEquals;

public class Common {

    public static Offer createOffer(int index) {
        return new Offer("Offer" + index,
                "Description" + index,
                1000,
                Currency.GBP);
    }

    public static void assertMatch(Offer offer1, Offer offer2){
        assertEquals(offer1.getId(), offer2.getId());
        assertEquals(offer1.getName(), offer2.getName());
        assertEquals(offer1.getDescription(), offer2.getDescription());
        assertEquals(offer1.getPrice().getPrice(), offer2.getPrice().getPrice(), 0.001);
        assertEquals(offer1.getPrice().getCurrency().getName(), offer2.getPrice().getCurrency().getName());
    }
}
