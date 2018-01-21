package com.wpshop.service;

import com.wpshop.Application;
import com.wpshop.model.Offer;
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
@WebAppConfiguration
public class OfferServiceTest {

    @Autowired
    private MessageCatalogue catalogue;

    @Autowired
    private OfferService service;

    @Before
    public void setup() {
        service.clear();
    }

    @Test
    public void createOffer() {
        hAddOffer(1);
        hCheckOfferExists(1);
    }

    @Test
    public void createEquivalentOffers() {
        Offer offer1 = hAddOffer(1);
        Offer offer2 = hAddOffer(1);
        hCheckOfferExists(offer1.getId());
        hCheckOfferExists(offer2.getId());
        assertNotEquals(offer1.getId(), offer2.getId());
    }

    @Test
    public void findAllOffers() {
        int maxSize = 5;
        Offer[] created = new Offer[maxSize];
        for (int i = 0; i < maxSize; i++) {
            created[i] = hAddOffer(i);
        }
        List<Offer> offers = service.findAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{maxSize}), maxSize, offers.size());
        for (int i = 0; i < offers.size(); i++) {
            Common.assertMatch(offers.get(i), created[i]);
        }
    }

    @Test
    public void findAllOffersWhenEmpty() {
        List<Offer> offers = service.findAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{0}), 0, offers.size());
    }

    @Test
    public void findOfferById() {
        Offer offer = hAddOffer(1);
        Optional<Offer> found = service.findOfferById(offer.getId());
        assertTrue(catalogue.get("test.fail.offerNotFound"), found.isPresent());
        Common.assertMatch(offer, found.get());
    }

    @Test
    public void findNonExistingOffer() {
        long id = 1000;
        Offer offer = hAddOffer(1);
        Optional<Offer> found = service.findOfferById(offer.getId());
        assertTrue(catalogue.get("test.fail.offerNotFound"), found.isPresent());
        Common.assertMatch(offer, found.get());
        found = service.findOfferById(id);
        assertFalse(catalogue.get("test.fail.offerExists", new Long[]{id}), found.isPresent());
    }

    @Test
    public void clear() {
        hAddOffer(1);
        List<Offer> offers = service.findAllOffers();
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, offers.size());
        service.clear();
        offers = service.findAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers"), 0, offers.size());
    }

    private void hCheckOfferExists(long id) {
        Optional<Offer> offer = service.findOfferById(id);
        assertTrue(catalogue.get("test.fail.offerNotFound"), offer.isPresent());
    }

    private Offer hAddOffer(int index) {
        Offer offer = Common.createOffer(index);
        offer = service.createOffer(offer);
        return offer;
    }
}