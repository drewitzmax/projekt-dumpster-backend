package com.cf.skipdiving.rest;

import com.cf.skipdiving.jpa.crud.OfferRepository;
import com.cf.skipdiving.jpa.crud.ProviderRepository;
import com.cf.skipdiving.jpa.entity.Offer;
import com.cf.skipdiving.jpa.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
public class OfferController {
    @Autowired
    private OfferRepository offerRepo;
    @Autowired
    private ProviderRepository providerRepo;

    @PostMapping(path="/offer", consumes = "application/json")
    public ResponseEntity<String> createOffer(@RequestBody Offer offer){
        offer.setId(BigInteger.ZERO);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        offer.setProvider(providerRepo.findByEmail(auth.getName()));

        offer.setAmountRemaining(offer.getAmountRemaining());
        
        try{
            offerRepo.save(offer);
            return new ResponseEntity<>("Offer successfully created", HttpStatus.CREATED);
        } catch( Exception e){
            return new ResponseEntity<>("Offer couldn't be created", HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path="/offer", produces = "application/json")
    public ResponseEntity<Iterable<Offer>> getAllOffers(){
        Iterable<Offer> offers = offerRepo.findAll();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
}
