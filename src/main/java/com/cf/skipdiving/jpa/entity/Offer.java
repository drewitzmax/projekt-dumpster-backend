package com.cf.skipdiving.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name="offer", schema = "skip_diving")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="offer_id")
    private BigInteger id;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String describtion;
    @Column(name="amount_offered", updatable = false)
    private int amountOffered;
    @Column(name="amount_remaining")
    private int amountRemaining;

    @ManyToOne()
    @JoinTable(name="provider_offer", schema = "skip_diving",joinColumns=@JoinColumn(name="offer_id"), inverseJoinColumns = @JoinColumn(name="provider_id") )
    private Provider provider;
    @ManyToMany
    @JoinTable(name="offer_user", schema = "skip_diving", joinColumns = @JoinColumn(name="offer_id"), inverseJoinColumns = @JoinColumn(name="user_id"))
    @JsonIgnoreProperties("orderHistory")
    private List<User> claimers = new ArrayList<>();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public int getAmountOffered() {
        return amountOffered;
    }

    public void setAmountOffered(int amountOffered) {
        this.amountOffered = amountOffered;
    }

    public int getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(int amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<User> getClaimers() {
        return claimers;
    }

    public void setClaimers(List<User> claimers) {
        this.claimers = claimers;
    }

    public boolean claim(User claimer){
        if(isAvailable()){
            claimers.add(claimer);
            amountRemaining -= 1;
            return true;
        } else {
            return false;
        }
    }

    public boolean isAvailable(){
        return amountRemaining > 0;
    }
}
