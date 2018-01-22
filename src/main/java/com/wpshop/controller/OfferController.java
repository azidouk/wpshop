package com.wpshop.controller;

import com.wpshop.model.Offer;
import com.wpshop.service.OfferService;

import java.util.Collection;
import java.util.List;
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

    @Autowired
    private OfferDTOtoDomain linker;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Collection<OfferDTO>> getAllOffers() {
        List<Offer> offers = service.getAllOffers();
        return new ResponseEntity<>(linker.toResourceCollection(offers), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        return service.getOfferById(id)
                .map(offer -> new ResponseEntity<>(linker.toResource(offer), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offer) {
        return new ResponseEntity<>(linker.toResource(service.createOffer(offer.getName(), offer.getDescription(), offer.getPrice())),
                HttpStatus.CREATED);
    }
}