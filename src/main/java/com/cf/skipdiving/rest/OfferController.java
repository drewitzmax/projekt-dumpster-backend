package com.cf.skipdiving.rest;

import com.cf.skipdiving.jpa.crud.OfferRepository;
import com.cf.skipdiving.jpa.crud.ProviderRepository;
import com.cf.skipdiving.jpa.crud.UserRepository;
import com.cf.skipdiving.jpa.entity.Offer;
import com.cf.skipdiving.jpa.entity.Provider;
import com.cf.skipdiving.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class OfferController {
    @Autowired
    private OfferRepository offerRepo;
    @Autowired
    private ProviderRepository providerRepo;
    @Autowired
    private UserRepository userRepo;

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

    @GetMapping(path="/offer/orderlist")
    public ResponseEntity<List<Offer>> getOwnOrders(){
        List<Offer> orders = offerRepo.findByClaimersContains(getCurrentUser());
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @GetMapping(path="/offer/offerlist")
    public ResponseEntity<List<Offer>> getOwnOffers(){
        List<Offer> orders = offerRepo.findByProvider(getCurrentProvider());
        if(orders.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(orders, HttpStatus.OK);
        }
    }

    @PostMapping(path="/offer/claim/{id}")
    public ResponseEntity<String> claimOffer(@PathVariable BigInteger id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User claimer = userRepo.findByUsername(auth.getName());

        Offer offer = offerRepo.findById(id).get();

        if(offer.isAvailable()){
            offer.claim( claimer );
            offerRepo.save( offer );
            return new ResponseEntity<>("Successfully claimed offer", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Couldn't claim offer", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/offer/{id}")
    public ResponseEntity<String> deleteOfferById(@PathVariable BigInteger id){
        Offer offer = offerRepo.findById(id).get();
        if(isOwner(getCurrentProvider(),offer)){
            offer.deleteAssociations();
            offerRepo.delete(offer);
            return new ResponseEntity<>("Offer successfully deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("You are not the owner of this offer", HttpStatus.UNAUTHORIZED);
    }

    private Provider getCurrentProvider(){
        return providerRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private User getCurrentUser(){
        return userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private boolean isOwner(Provider provider, Offer offer){
        try{
            return provider.getId() == offer.getProvider().getId();
        } catch (Exception e){
            return false;
        }
    }

}
