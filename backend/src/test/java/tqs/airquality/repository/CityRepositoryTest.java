package tqs.airquality.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tqs.airquality.entities.City;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void findByNameAndIdCityTest(){
        City city = new City("Aveiro", "Aveiro", "Portugal");
        testEntityManager.persistAndFlush(city);

        //by name
        City foundCity = cityRepository.findByName(city.getName());
        assertThat(foundCity).isEqualTo(city);

        // by id
        City foundCityById = cityRepository.findById(city.getId()).orElse(null);
        assertThat(foundCityById).isNotNull();
        assertThat(foundCityById.getName()).isEqualTo( city.getName());
    }

    @Test
    void findAllCitiesTest(){
        City c1 = new City("Aveiro", "Aveiro", "Portugal");
        City c2 = new City("Porto", "Porto", "Portugal");
        City c3 = new City("Agueda", "Aveiro", "Portugal");

        testEntityManager.persist(c1);
        testEntityManager.persist(c2);
        testEntityManager.persist(c3);
        testEntityManager.flush();

        List<City> cities = cityRepository.findAll();

        assertThat(cities).hasSize(3).extracting(City::getName).containsOnly(c1.getName(), c2.getName(), c3.getName());
    }

}
