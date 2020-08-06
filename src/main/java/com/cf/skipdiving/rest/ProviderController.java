package com.cf.skipdiving.rest;

import com.cf.skipdiving.jpa.crud.ProviderRepository;
import com.cf.skipdiving.jpa.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class ProviderController {
    @Autowired
    private ProviderRepository providerRepo;

    //Provider Mapping
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

    @GetMapping(path = "/provider")
    public ResponseEntity<List<Provider>>getAllProviders(){
        List<Provider> providers = convertIterableToList( providerRepo.findAll() );
        if(providers.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(providers, HttpStatus.OK);
        }
    }

    //Category Mapping
    @GetMapping(path = "/provider/category/{category}")
    public ResponseEntity<List<Provider>>getAllCategory(@PathVariable String category){
        List<Provider> providers = providerRepo.findAllByCategory(category);
        if(providers.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(providers, HttpStatus.OK);
        }
    }

    private List<Provider> convertIterableToList(Iterable<Provider> iterable) {
        List<Provider> list = new ArrayList<>();
        for (Provider provider : iterable) {
            list.add(provider);
        }
        return list;
    }
}