package com.cf.skipdiving.jpa.entity;

import com.cf.skipdiving.exception.ActionNotExecutedException;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="provider", schema = "skip_diving")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="provider_id", nullable = false, unique = true, updatable = false)
    public BigInteger id;
    @Column(name="name")
    public String name;
    @Column(name="address")
    public String address;
    @Column(name="phone_number")
    public String phoneNumber;
    @Column(name="email")
    public String email;
    @Column(name="password")
    public String password;
    @Column(name="homepage")
    public String homepageUrl;
    @ElementCollection
    @CollectionTable(name="provider_image",schema = "skip_diving", joinColumns=@JoinColumn(name="provider_id"))
    @Column(name="photo_url", nullable = false)
    public Set<String> photos = new HashSet<>();

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
        this.password = password;
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
}