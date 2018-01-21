package com.wpshop.controller;

import com.wpshop.model.Offer;
import com.wpshop.service.OfferService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;

@RestController
@ExposesResourceFor(Offer.class)
@RequestMapping(value = "/offer", produces = "application/json")
public class OfferController {

    @Autowired
    private OfferService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Collection<Offer>> getAllOffers() {
        List<Offer> offers = service.findAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Offer> getOffer(@PathVariable Long id) {
        return service.findOfferById(id)
                .map(offer -> new ResponseEntity<>(offer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        Optional<Offer> created =  Optional.ofNullable(service.createOffer(offer));
        return created
                .map(item -> new ResponseEntity<>(item, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}