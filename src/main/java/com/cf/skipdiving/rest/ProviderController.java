package com.cf.skipdiving.rest;

import com.cf.skipdiving.jpa.crud.ProviderRepository;
import com.cf.skipdiving.jpa.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class ProviderController {
    @Autowired
    private ProviderRepository providerRepo;

    @PostMapping(path="/provider", consumes = "application/json")
    public ResponseEntity<String> signUp(@RequestBody Provider provider){
        provider.setId(BigInteger.ZERO);
        try{
            providerRepo.save(provider);
            return new ResponseEntity<>("Provider successfully created", HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
