package com.cf.skipdiving.jpa.crud;

import com.cf.skipdiving.jpa.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface UserRepository extends CrudRepository<User, BigInteger> {

}
