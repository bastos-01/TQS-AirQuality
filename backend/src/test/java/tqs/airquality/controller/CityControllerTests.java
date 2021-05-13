package tqs.airquality.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.airquality.entities.City;
import tqs.airquality.service.CityServiceImplementation;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CityRestController.class)
class CityControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CityServiceImplementation service;

    @BeforeEach
    void setUp(){
    }

    @Test
    void getAllCitiesTest() throws Exception {
        ArrayList<City> cidades = new ArrayList<>();
        cidades.add(new City("Aveiro", "Aveiro", "Portugal"));
        cidades.add(new City("Agueda", "Aveiro", "Portugal"));

        given(service.getCities("Portugal", "Aveiro")).willReturn(cidades);

        mvc.perform(get("/api/list/{country}/{state}", "Portugal", "Aveiro").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]", is("Aveiro"))).andExpect(jsonPath("$[1]", is("Agueda")));
    }

    @Test
    void getAllStatesTest() throws Exception {
        ArrayList<String> states = new ArrayList<>();
        states.add("Aveiro");
        states.add("Porto");

        given(service.getStates("Portugal")).willReturn(states);
        mvc.perform(get("/api/list/{country}", "Portugal").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]", is("Aveiro"))).andExpect(jsonPath("$[1]", is("Porto")));

    }

    @Test
    void getAllCountriesTest() throws Exception {
        ArrayList<String> countries = new ArrayList<>();
        countries.add("USA");
        countries.add("Portugal");

        given(service.getCountries()).willReturn(countries);
        mvc.perform(get("/api/list/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]", is("USA"))).andExpect(jsonPath("$[1]", is("Portugal")));

    }

    @Test
    void getCityDataTest() throws Exception {
        City city = new City("Aveiro", "Aveiro", "Portugal");

        given(service.getCityData("Portugal", "Aveiro", "Aveiro")).willReturn(city);
        mvc.perform(get("/api/{country}/{state}/{city}", "Portugal", "Aveiro", "Aveiro").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Aveiro")));

    }

    @Test
    void getCacheDetailsTest() throws Exception {
        HashMap<String, Integer> cacheMap = new HashMap<>();
        cacheMap.put("hits", 2);
        cacheMap.put("misses", 1);
        cacheMap.put("requests", 3);

        given(service.getCacheDetails()).willReturn(cacheMap);
        mvc.perform(get("/api/cache").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hits", is(2)))
                .andExpect(jsonPath("$.misses", is(1)))
                .andExpect(jsonPath("$.requests", is(3)));

    }
}
