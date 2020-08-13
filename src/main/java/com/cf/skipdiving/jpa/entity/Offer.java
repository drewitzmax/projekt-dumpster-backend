package com.cf.skipdiving.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String description;
    @Column(name="amount_offered", updatable = false)
    private int amountOffered;
    @Column(name="amount_remaining")
    private int amountRemaining;

    @ManyToOne()
    @JoinTable(name="provider_offer", schema = "skip_diving",joinColumns=@JoinColumn(name="offer_id"), inverseJoinColumns = @JoinColumn(name="provider_id") )
    @JsonIgnoreProperties("offers")
    private Provider provider;
    @ManyToMany
    @JoinTable(name="offer_user", schema = "skip_diving", joinColumns = @JoinColumn(name="offer_id"), inverseJoinColumns = @JoinColumn(name="user_id"))
    @JsonIgnoreProperties("orderHistory")
    private Set<User> claimers = new HashSet<>();

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
        return description;
    }

    public void setDescribtion(String describtion) {
        this.description = describtion;
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

    public Set<User> getClaimers() {
        return claimers;
    }

    public void setClaimers(Set<User> claimers) {
        this.claimers = claimers;
    }

    public void claim(User claimer) {
        claimers.add(claimer);
        claimer.getOrderHistory().add(this);
        amountRemaining -= 1;
    }

    public boolean isAvailable(){
        return amountRemaining > 0;
    }

    public void deleteAssociations(){
        removeProvider();
        removeAllClaimers();
    }

    private void removeProvider(){
        provider.getOffers().remove(this);
        provider = null;
    }

    protected void removeAllClaimers(){
        for(User claimer: claimers){
            claimer.getOrderHistory().remove(this);
            claimers.remove(claimer);
        }
    }

}
