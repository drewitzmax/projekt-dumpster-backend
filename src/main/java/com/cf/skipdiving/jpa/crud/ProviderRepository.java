package com.cf.skipdiving.jpa.crud;

import com.cf.skipdiving.jpa.entity.Provider;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface ProviderRepository extends CrudRepository<Provider, BigInteger> {
}
