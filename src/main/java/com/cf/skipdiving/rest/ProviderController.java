package com.cf.skipdiving.rest;

import com.cf.skipdiving.enums.ProviderClassification;
import com.cf.skipdiving.jpa.crud.ProviderRepository;
import com.cf.skipdiving.jpa.entity.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin()
@RestController
public class ProviderController {
    @Autowired
    private ProviderRepository providerRepo;

    //Provider Mapping
    @PostMapping(path="/provider", consumes = "application/json")
    public ResponseEntity<String> signUp(@RequestBody Provider provider){
        provider.setId(BigInteger.ZERO);
        if(providerRepo.findByEmail(provider.getEmail()) != null){
            return new ResponseEntity<>("Email already in Use!", HttpStatus.CONFLICT);
        }
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
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(providers, HttpStatus.OK);
        }
    }

    //Category Mapping
    @GetMapping(path = "/provider/category")
    public ResponseEntity<List<Provider>>getAllCategory(@RequestParam String category){
        List<Provider> providers = providerRepo.findAllByCategory(category);
        if(providers.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(providers, HttpStatus.OK);
        }
    }

    //Classification Mapping
    @GetMapping(path = "provider/classification")
    public ResponseEntity<List<Provider>>getClassification(@RequestParam ProviderClassification classification){
        List<Provider> providers = providerRepo.findAllByClassification(classification);
        if (providers.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(providers, HttpStatus.OK);
        }
    }

    @DeleteMapping(path="/provider")
    public ResponseEntity<String>deleteProviderAccount(){
        try{
            Provider current = getCurrentProvider();
            current.deleteAssociations();
            providerRepo.delete(current);
            return new ResponseEntity<>("Account successfully deleted", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Account couldn't be deleted", HttpStatus.BAD_REQUEST);
        }
    }

    //
    private List<Provider> convertIterableToList(Iterable<Provider> iterable) {
        List<Provider> list = new ArrayList<>();
        for (Provider provider : iterable) {
            list.add(provider);
        }
        return list;
    }

    private Provider getCurrentProvider(){
        return providerRepo.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}