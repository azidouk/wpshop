package com.wpshop.model;

import com.wpshop.Application;
import com.wpshop.utils.Common;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
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
        Common.createOffer(store, 1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
    }

    @Test
    public void createEquivalentOffers() {
        Common.createOffer(store, 1);
        Common.createOffer(store, 1);
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{2}), 2, store.size());
    }

    @Test
    public void findAllOffers() {
        Common.findAllOffers(store, catalogue);
    }

    @Test
    public void findAllOffersWhenEmpty() {
        List<Offer> offers = store.getAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{0}), offers.size(), 0);
    }

    @Test
    public void findOfferById(){
        long id = 1;
        Offer offer = Common.createOffer(store, 1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
        Optional<Offer> found = store.getOfferById(id);
        assertTrue(catalogue.get("test.fail.offerNotFound"), found.isPresent());
        assertEquals(offer, found.get());
    }

    @Test
    public void findNonExistingOffer(){
        long id = 1000;
        Common.createOffer(store, 1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
        Optional<Offer> offer = store.getOfferById(id);
        assertFalse(catalogue.get("test.fail.offerExists", new Long[]{id}), offer.isPresent());
    }

    @Test
    public void clear() {
        Common.createOffer(store, 1);
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, store.size());
        List<Offer> offers = store.getAllOffers();
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, offers.size());
        store.clear();
        assertEquals(catalogue.get("test.fail.storeIsNotEmpty"), 0, store.size());
    }
}