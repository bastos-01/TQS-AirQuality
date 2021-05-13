package tqs.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tqs.airquality.entities.City;
import tqs.airquality.repository.CityRepository;
import tqs.airquality.repository.PollutionRepository;
import tqs.airquality.repository.WeatherRepository;
import tqs.airquality.service.CityServiceImplementation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CityRestController {

    public CityRepository cityRepository;

    public PollutionRepository pollutionRepository;

    public WeatherRepository weatherRepository;

    @Autowired
    public CityServiceImplementation cityServiceImplementation;

    @GetMapping(value = "/list/{country}/{state}", produces="application/json")
    public List<String> getAllCities(@PathVariable(value = "country") String country,
                                   @PathVariable(value = "state") String state) throws IOException, URISyntaxException {

        ArrayList<City> cidades = this.cityServiceImplementation.getCities(country, state);
        ArrayList<String> nomes_cidades = new ArrayList<>();
        for(City c: cidades){
            //cityRepository.save(c);
            nomes_cidades.add(c.getName());
        }
        System.out.println(cidades.get(0).toString());
        return nomes_cidades;
    }

    @GetMapping(value = "/list/{country}", produces="application/json")
    public List<String> getAllStates(@PathVariable(value = "country") String country) throws IOException, URISyntaxException {

        ArrayList<String> states = this.cityServiceImplementation.getStates(country);

        return states;
    }

    @GetMapping(value = "/list", produces="application/json")
    public List<String> getAllCountries() throws IOException, URISyntaxException {

        ArrayList<String> countries = this.cityServiceImplementation.getCountries();

        return countries;
    }

    @GetMapping(value = "/{country}/{state}/{city}", produces = "application/json")
    public City getCityData(@PathVariable(value = "country") String country,
                            @PathVariable(value = "state") String state,
                            @PathVariable(value = "city") String city) throws IOException, URISyntaxException {

        City city_data = this.cityServiceImplementation.getCityData(country, state, city);

        return city_data;

    }

    @GetMapping(value = "/cache", produces = "application/json")
    public HashMap<String, Integer> getCacheDetails(){
        return this.cityServiceImplementation.getCacheDetails();
    }




}
