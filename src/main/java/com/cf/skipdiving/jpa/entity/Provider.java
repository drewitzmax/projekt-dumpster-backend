package com.cf.skipdiving.jpa.entity;

import com.cf.skipdiving.enums.ProviderClassification;
import com.cf.skipdiving.exception.ActionNotExecutedException;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="provider", schema = "skip_diving")
public class Provider {
    private static BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="provider_id", nullable = false, unique = true, updatable = false)
    private BigInteger id;
    @Column(name="name")
    private String name;
    @Column(name="address")
    private String address;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="email", unique = true)
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="homepage")
    private String homepageUrl;
    @Column(name = "category")
    private String category;
    @Enumerated(EnumType.STRING)
    private ProviderClassification classification;
    @ElementCollection
    @CollectionTable(name="provider_image",schema = "skip_diving", joinColumns=@JoinColumn(name="provider_id"))
    @Column(name="photo_url", nullable = false)
    private Set<String> photos = new HashSet<>();

    @OneToMany(mappedBy = "provider", cascade = CascadeType.REMOVE)
    private Set<Offer> offers = new HashSet<>();

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public Set<String> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<String> photos) {
        this.photos = photos;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
    
    public ProviderClassification getClassification() {
        return classification;
    }

    public void setClassification(ProviderClassification classification) {
        this.classification = classification;
    }

    public void addPhotoUrl(String url){
        if(!photos.contains(url)) {
            photos.add(url);
        } else {
            throw new ActionNotExecutedException("Given Url already in set");
        }
    }

    public void removePhotoUrl(String url){
        if(photos.contains(url)) {
            photos.remove(url);
        } else {
            throw new ActionNotExecutedException("Given Url is not in Set");
        }
    }

    public void deleteAssociations(){
        for(Offer offer: offers){
            offer.removeAllClaimers();
        }
    }
}
