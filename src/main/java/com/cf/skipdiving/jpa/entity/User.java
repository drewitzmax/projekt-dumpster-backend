package com.cf.skipdiving.jpa.entity;

import com.cf.skipdiving.jpa.crud.OfferRepository;
import com.cf.skipdiving.jpa.crud.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="sd_user", schema = "skip_diving")
public class User {
    private static BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    public BigInteger id;
    @Column(name="name")
    public String name;
    @Column(name="lastname")
    public String lastname;
    @Column(name="email")
    public String email;
    @Column(name="username")
    public String username;
    @Column(name="password")
    public String password;

    @ManyToMany(mappedBy = "claimers", cascade = CascadeType.REFRESH)
    public Set<Offer> orderHistory = new HashSet<>();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public Set<Offer> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(Set<Offer> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public void removeOffer(Offer offer){
        this.orderHistory.remove(offer);
        offer.getClaimers().remove(this);
    }

    public void deleteAssociations(){
        for(Offer offer: orderHistory){
            removeOffer(offer);
        }
    }
}
