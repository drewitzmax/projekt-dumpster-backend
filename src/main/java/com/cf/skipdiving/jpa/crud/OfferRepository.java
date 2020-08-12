package com.cf.skipdiving.jpa.crud;

import com.cf.skipdiving.jpa.entity.Offer;
import com.cf.skipdiving.jpa.entity.Provider;
import com.cf.skipdiving.jpa.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface OfferRepository extends CrudRepository<Offer, BigInteger> {
    public List<Offer> findByClaimersContains(User user);
    public List<Offer> findByProvider(Provider provider);
}
