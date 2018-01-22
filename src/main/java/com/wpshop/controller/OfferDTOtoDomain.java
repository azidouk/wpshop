package com.wpshop.controller;

import com.wpshop.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferDTOtoDomain {

    @Autowired
    private EntityLinks entityLinks;

    public OfferDTO toResource(Offer offer) {
        OfferDTO resource = new OfferDTO(offer.getName(), offer.getDescription(), offer.getPrice());
        resource.setOfferId(offer.getId());
        final Link selfLink = entityLinks.linkToSingleResource(offer.getClass(), offer.getId());
        resource.add(selfLink.withSelfRel());
        return resource;
    }

    public Collection<OfferDTO> toResourceCollection(List<Offer> offers) {
        return offers.stream().map(this::toResource).collect(Collectors.toList());
    }
}
