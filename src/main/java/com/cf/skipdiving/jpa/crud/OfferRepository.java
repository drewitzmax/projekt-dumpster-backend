package com.cf.skipdiving.jpa.crud;

import com.cf.skipdiving.jpa.entity.Offer;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface OfferRepository extends CrudRepository<Offer, BigInteger> {
}
