package tqs.airquality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tqs.airquality.entities.City;
import tqs.airquality.service.CityServiceImplementation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CityRestController {

    @Autowired
    public CityServiceImplementation cityServiceImplementation;

    @GetMapping(value = "/list/{country}/{state}", produces="application/json")
    public List<String> getAllCities(@PathVariable(value = "country") String country,
                                   @PathVariable(value = "state") String state) throws IOException, URISyntaxException {

        ArrayList<City> cidades = this.cityServiceImplementation.getCities(country, state);
        ArrayList<String> nomesCidades = new ArrayList<>();
        for(City cidade: cidades){
            nomesCidades.add(cidade.getName());
        }
        return nomesCidades;
    }

    @GetMapping(value = "/list/{country}", produces="application/json")
    public List<String> getAllStates(@PathVariable(value = "country") String country) throws IOException, URISyntaxException {

        return this.cityServiceImplementation.getStates(country);
    }

    @GetMapping(value = "/list", produces="application/json")
    public List<String> getAllCountries() throws IOException, URISyntaxException {

        return this.cityServiceImplementation.getCountries();
    }

    @GetMapping(value = "/{country}/{state}/{city}", produces = "application/json")
    public City getCityData(@PathVariable(value = "country") String country,
                            @PathVariable(value = "state") String state,
                            @PathVariable(value = "city") String city) throws IOException, URISyntaxException {

        return this.cityServiceImplementation.getCityData(country, state, city);

    }

    @GetMapping(value = "/cache", produces = "application/json")
    public Map<String, Integer> getCacheDetails(){
        return this.cityServiceImplementation.getCacheDetails();
    }


}
