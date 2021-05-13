package tqs.airquality.service;

import org.apache.tomcat.jni.Poll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.airquality.entities.City;
import tqs.airquality.entities.Pollution;
import tqs.airquality.entities.Weather;
import tqs.airquality.repository.CityRepository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ServiceTests {

    @Mock(lenient = true)
    private CityRepository cityRepository;

    @InjectMocks
    private CityServiceImplementation cityServiceImplementation;

    @BeforeEach
    void setUp() throws InterruptedException {
        this.cityServiceImplementation = new CityServiceImplementation();
        City c1 = new City("Aveiro", "Aveiro", "Portugal");
        City c2 = new City("Agueda", "Aveiro", "Portugal");
        ArrayList<City> cidades = new ArrayList<>();
        cidades.add(c1);
        cidades.add(c2);

        when(cityRepository.findByName(c1.getName())).thenReturn(c1);
        when(cityRepository.findByName(c2.getName())).thenReturn(c2);
        when(cityRepository.findAll()).thenReturn(cidades);
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void getCityTest() throws IOException, URISyntaxException {
        String cityName = "Aveiro";
        City foundCity = cityServiceImplementation.getCityData("Portugal", "Aveiro", "Aveiro");

        assertThat(foundCity.getName()).isEqualTo(cityName);
        assertThat(foundCity.getCountry()).isEqualTo("Portugal");
    }

}
