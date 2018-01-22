package com.wpshop.utils;

import com.wpshop.model.Currency;
import com.wpshop.model.Offer;
import com.wpshop.model.Price;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Common {

    public static Offer createOffer(RepositoryManager manager, int index) {
        return manager.createOffer("Offer" + index, "Description" + index,
                new Price(1000, Currency.GBP));
    }

    private static Offer[] createMultipleOffers(RepositoryManager manager, int maxSize) {
        Offer[] created = new Offer[maxSize];
        for (int i = 0; i < maxSize; i++) {
            created[i] = createOffer(manager, i);
        }
        return created;
    }

    public static void findAllOffers(RepositoryManager manager, MessageCatalogue catalogue) {
        int maxSize = 5;
        Offer[] created = Common.createMultipleOffers(manager, maxSize);
        List<Offer> offers = manager.getAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{maxSize}), maxSize, offers.size());
        for (int i = 0; i < offers.size(); i++) {
            assertEquals(created[i], offers.get(i));
        }
    }
}
