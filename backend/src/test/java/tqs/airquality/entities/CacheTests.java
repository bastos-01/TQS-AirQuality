package tqs.airquality.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Semaphore;

class CacheTests {

    private Cache cache;

    @BeforeEach
    void setUp() throws InterruptedException {
        this.cache = new Cache(1);
    }

    @AfterEach
    void tearDown(){
        this.cache.clearCache();
    }

    @Test
    void addValueTest(){
        assertEquals(0, this.cache.getCacheSize());
        this.cache.addValue("Los Angeles", new City("Los Angeles", "California", "USA"));
        assertEquals(1, this.cache.getCacheSize());
        assertEquals(true, this.cache.containsCity("Los Angeles"));
    }

    @Test
    void deleteValueTest(){
        assertEquals(0, this.cache.getCacheSize());
        this.cache.addValue("Los Angeles", new City("Los Angeles", "California", "USA"));
        this.cache.addValue("Aveiro", new City("Aveiro", "Aveiro", "Portugal"));
        this.cache.deleteValue("Los Angeles");
        assertEquals(1, this.cache.getCacheSize());
        assertEquals(false, this.cache.containsCity("Los Angeles"));
    }

    @Test
    void cleanByTimeTest() throws InterruptedException {
        this.cache.addValue("Los Angeles", new City("Los Angeles", "California", "USA"));
        Thread.sleep(2000);
        assertEquals(0, this.cache.getCacheSize());
    }

    @Test
    void getHitsAndMissesAndRequestsTest(){
        this.cache.addValue("Aveiro", new City("Aveiro", "Aveiro", "Portugal"));
        this.cache.getCityFromCache("Aveiro");
        this.cache.getCityFromCache("Aveiro");
        this.cache.getCityFromCache("null");
        assertEquals(2, this.cache.getHits());
        assertEquals(1, this.cache.getMisses());
        assertEquals(3, this.cache.getRequests());
    }

}
