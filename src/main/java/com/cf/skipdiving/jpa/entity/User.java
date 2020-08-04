package com.cf.skipdiving.jpa.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name="sd_user", schema = "skip_diving")
public class User {
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
        this.password = password;
    }
}
