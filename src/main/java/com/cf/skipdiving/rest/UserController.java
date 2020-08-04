package com.cf.skipdiving.rest;

import com.cf.skipdiving.jpa.crud.UserRepository;
import com.cf.skipdiving.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping(path="/user", consumes = "application/json")
    public ResponseEntity<Void> signUp(@RequestBody User user){
        try{
            userRepo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
