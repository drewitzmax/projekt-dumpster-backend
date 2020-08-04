package com.cf.skipdiving.jpa.crud;

import com.cf.skipdiving.jpa.entity.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProviderRepositoryTest {
    @Autowired
    ProviderRepository repo;

    @Test
    public void createProviderTest(){
        Provider provider = new Provider();
        provider.setName("First Restaurant");
        provider.setAddress("Strasse 1 \n 1030 Wien");
        provider.setEmail("firstrestaurant@test.at");
        provider.setPassword("test");
        provider.setPhoneNumber("05151-05548648");
        provider.setHomepageUrl("www.test.at");
        provider.getPhotos().add("blubb");
        provider.getPhotos().add("blabb");

        repo.save(provider);
    }
}
