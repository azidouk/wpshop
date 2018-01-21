package com.wpshop.model;

import com.wpshop.Application;
import com.wpshop.utils.MessageCatalogue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import com.wpshop.utils.Common;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class StoreTest {

    @Autowired
    private MessageCatalogue catalogue;

    @Autowired
    private Store store;

    @Before
    public void setup() {
        store.clear();
        assertEquals(catalogue.get("test.fail.storeIsNotEmpty"), 0, store.size());
    }

    @Test
    public void createOffer() {
        hAddOffer(1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
    }

    @Test
    public void createEquivalentOffers() {
        hAddOffer(1);
        hAddOffer(1);
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{2}), 2, store.size());
    }

    @Test
    public void findAllOffers() {
        int maxSize = 5;
        Offer[] created = new Offer[maxSize];
        for (int i = 0; i < maxSize; i++) {
            created[i] = hAddOffer(i);
        }
        List<Offer> offers = store.findAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{maxSize}), offers.size(), maxSize);
        for (int i = 0; i < offers.size(); i++) {
            Common.assertMatch(offers.get(i), created[i]);
        }
    }

    @Test
    public void findAllOffersWhenEmpty() {
        List<Offer> offers = store.findAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{0}), offers.size(), 0);
    }

    @Test
    public void findOfferById(){
        long id = 1;
        Offer offer = hAddOffer(1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
        Optional<Offer> found = store.findOfferById(id);
        assertTrue(catalogue.get("test.fail.offerNotFound"), found.isPresent());
        Common.assertMatch(offer, found.get());
    }

    @Test
    public void findNonExistingOffer(){
        long id = 1000;
        hAddOffer(1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
        Optional<Offer> offer = store.findOfferById(id);
        assertFalse(catalogue.get("test.fail.offerExists", new Long[]{id}), offer.isPresent());
    }

    @Test
    public void clear() {
        hAddOffer(1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
        List<Offer> offers = store.findAllOffers();
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, offers.size());
        store.clear();
        assertEquals(catalogue.get("test.fail.storeIsNotEmpty"), 0, store.size());
    }

    private Offer hAddOffer(int index) {
        Offer offer = Common.createOffer(index);
        offer = store.createOffer(offer);
        return offer;
    }
}