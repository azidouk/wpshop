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
        Common.createOffer(service, 1);
        hCheckOfferExists(1);
    }

    @Test
    public void createEquivalentOffers() {
        Offer offer1 = Common.createOffer(service, 1);
        Offer offer2 = Common.createOffer(service, 1);
        hCheckOfferExists(offer1.getId());
        hCheckOfferExists(offer2.getId());
        assertNotEquals(offer1.getId(), offer2.getId());
    }

    @Test
    public void findAllOffers() {
        Common.findAllOffers(service, catalogue);
    }

    @Test
    public void findAllOffersWhenEmpty() {
        List<Offer> offers = service.getAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers", new Integer[]{0}), 0, offers.size());
    }

    @Test
    public void findOfferById() {
        Offer offer = Common.createOffer(service, 1);
        Optional<Offer> found = service.getOfferById(offer.getId());
        assertTrue(catalogue.get("test.fail.offerNotFound"), found.isPresent());
        assertEquals(offer, found.get());
    }

    @Test
    public void findNonExistingOffer() {
        long id = 1000;
        Offer offer = Common.createOffer(service, 1);
        Optional<Offer> found = service.getOfferById(offer.getId());
        assertTrue(catalogue.get("test.fail.offerNotFound"), found.isPresent());
        assertEquals(offer, found.get());
        found = service.getOfferById(id);
        assertFalse(catalogue.get("test.fail.offerExists", new Long[]{id}), found.isPresent());
    }

    @Test
    public void clear() {
        Common.createOffer(service, 1);
        List<Offer> offers = service.getAllOffers();
        assertEquals(catalogue.get("test.fail.failSingleOffer"), 1, offers.size());
        service.clear();
        offers = service.getAllOffers();
        assertEquals(catalogue.get("test.fail.failMultipleOffers"), 0, offers.size());
    }

    private void hCheckOfferExists(long id) {
        Optional<Offer> offer = service.getOfferById(id);
        assertTrue(catalogue.get("test.fail.offerNotFound"), offer.isPresent());
    }
}