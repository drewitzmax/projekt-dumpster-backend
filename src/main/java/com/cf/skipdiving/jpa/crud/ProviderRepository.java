package com.cf.skipdiving.jpa.crud;

import com.cf.skipdiving.enums.ProviderClassification;
import com.cf.skipdiving.jpa.entity.Provider;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, BigInteger> {
    public List<Provider> findAllByCategory(String category);
    public List<Provider> findAllByClassification(ProviderClassification classification);
    public Provider findByEmail(String email);
}
