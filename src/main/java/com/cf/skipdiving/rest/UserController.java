package com.cf.skipdiving.rest;

import com.cf.skipdiving.jpa.crud.UserRepository;
import com.cf.skipdiving.jpa.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping(path="/user", consumes = "application/json")
    public ResponseEntity<String> signUp(@RequestBody User user){
        user.setId(BigInteger.ZERO);
        try{
            if(userRepo.findByEmail(user.getEmail()) != null)
                return new ResponseEntity<>("Email already in use", HttpStatus.CONFLICT);
            if(userRepo.findByUsername(user.getUsername()) != null)
                return new ResponseEntity<>("Username already taken", HttpStatus.CONFLICT);
            userRepo.save(user);
            return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/user")
    public ResponseEntity<String> deleteAccount(){
        try {
            User current = getCurrentUser();
            current.deleteAssociations();
            userRepo.delete(current);
            return new ResponseEntity<>("Account successfully delteted", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Account couln't be deleted\n"+e.toString(),HttpStatus.valueOf(500));
        }
    }

    private User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(username);
    }
}
