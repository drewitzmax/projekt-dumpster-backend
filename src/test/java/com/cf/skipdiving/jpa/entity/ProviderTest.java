package com.cf.skipdiving.jpa.entity;

import com.cf.skipdiving.exception.ActionNotExecutedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProviderTest {
    private Provider provider;
    @BeforeEach
    public void setUpTest(){
        provider = new Provider();
    }

    @Test
    public void testAddingPhotos(){
        provider.addPhotoUrl("url1");
        Assertions.assertTrue(provider.getPhotos().contains("url1"));
    }

    @Test
    public void testRemovingPhotos(){
        provider.addPhotoUrl("url1");
        Assertions.assertTrue(provider.getPhotos().contains("url1"));
        provider.removePhotoUrl("url1");
        Assertions.assertFalse(provider.getPhotos().contains("url1"));
    }

    @Test
    public void testRemovingPhotoNotExisting(){
        Assertions.assertThrows(ActionNotExecutedException.class, ()->{
           provider.removePhotoUrl("url1");
        });
    }

    @Test
    public void testAddingDuplicatePhoto(){
        provider.addPhotoUrl("url1");
        Assertions.assertThrows(ActionNotExecutedException.class, ()->{
            provider.addPhotoUrl("url1");
        });
    }
}
