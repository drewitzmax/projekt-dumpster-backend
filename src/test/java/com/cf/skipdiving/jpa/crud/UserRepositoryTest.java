package com.cf.skipdiving.jpa.crud;

import com.cf.skipdiving.jpa.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Test
    public void saveUserTest(){
        User user = new User();
        user.setId(BigInteger.ZERO);
        user.setName("Max");
        user.setLastname("Drewitz");
        user.setUsername("drewi");
        user.setEmail("drewitz.max@example.com");
        user.setPassword("test");

        Assertions.assertDoesNotThrow(() -> { repo.save(user); });
    }
}
