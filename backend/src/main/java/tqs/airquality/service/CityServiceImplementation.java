package tqs.airquality.service;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import tqs.airquality.entities.Cache;
import tqs.airquality.entities.City;
import tqs.airquality.entities.Pollution;
import tqs.airquality.entities.Weather;

@Service
public class CityServiceImplementation {

    private static final String PRIVATEKEY =  "d4e2fea5-a543-41a0-98ac-0d89382a07a1";
    private static final String URL = "https://api.airvisual.com/v2/";

    private CloseableHttpClient client;

    private Cache cache = new Cache(60);

    public CityServiceImplementation() throws InterruptedException {
        this.client = HttpClients.createDefault();
    }

    public ArrayList<City> getCities(String country, String state) throws IOException, URISyntaxException {
        return handleRequestAllCities(URL + "cities?state=" + state + "&country=" + country, country, state);
    }

    public ArrayList<String> getStates(String country) throws IOException, URISyntaxException {
        return handleRequestAllStatesOrCountries(URL + "states?country=" + country, 0);
    }

    public ArrayList<String> getCountries() throws IOException, URISyntaxException {
        return handleRequestAllStatesOrCountries(URL + "countries?", 1);
    }

    public City getCityData(String country, String state, String city) throws IOException, URISyntaxException {
        return handleRequestCityData(URL + "city?city=" + city +  "&state=" + state + "&country=" + country, country, state, city);
    }

    public City handleRequestCityData(String url, String country, String state, String city) throws URISyntaxException, IOException {

        City cityFromCache = cache.getCityFromCache(city);

        if(cityFromCache != null){
            return cityFromCache;
        }


        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject responseJson =  new JSONObject(response);

        //city data
        JSONObject data = new JSONObject(responseJson.get("data").toString());

        //coordinates
        JSONObject location = new JSONObject(data.get("location").toString());
        JSONArray coordinates = new JSONArray(location.get("coordinates").toString());
        Double latitude = Double.parseDouble(coordinates.get(0).toString());
        Double longitude = Double.parseDouble(coordinates.get(1).toString());

        //current
        JSONObject current = new JSONObject(data.get("current").toString());

        //weather
        JSONObject weatherJson = new JSONObject(current.get("weather").toString());
        String timeStamp = weatherJson.get("ts").toString();
        Double temperature = Double.parseDouble(weatherJson.get("tp").toString());
        Double pressure = Double.parseDouble(weatherJson.get("pr").toString());
        Double humidity = Double.parseDouble(weatherJson.get("hu").toString());
        Double windSpeed = Double.parseDouble(weatherJson.get("ws").toString());

        Weather weather = new Weather(temperature, pressure, humidity, windSpeed);

        //pollution
        JSONObject pollutionJson = new JSONObject(current.get("pollution").toString());
        Double aqius = Double.parseDouble(pollutionJson.get("aqius").toString());
        String mainus = pollutionJson.get("mainus").toString();
        Double aqicn = Double.parseDouble(pollutionJson.get("aqicn").toString());
        String maincn = pollutionJson.get("maincn").toString();

        Pollution pollution = new Pollution(aqius, aqicn, mainus, maincn);

        City fullCity = new City(city, state, country, latitude, longitude, timeStamp, weather, pollution);

        cache.addValue(city, fullCity);


        return fullCity;
    }

    public ArrayList<String> handleRequestAllStatesOrCountries(String url, int flag) throws URISyntaxException, IOException {

        ArrayList<String> values = new ArrayList<>();

        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject responseJson = new JSONObject(response);

        JSONArray data = new JSONArray(responseJson.get("data").toString());

        for (Object obj: data){
            JSONObject jsonObj = (JSONObject) obj;
            if (flag == 0)
                values.add(jsonObj.get("state").toString());
            else
                values.add(jsonObj.get("country").toString());

        }
        return values;
    }

    public ArrayList<City> handleRequestAllCities(String url, String country, String state) throws URISyntaxException, IOException {

        ArrayList<City> cidades = new ArrayList<>();

        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject responseJson = new JSONObject(response);

        JSONArray data = new JSONArray(responseJson.get("data").toString());

        for (Object obj: data){
            JSONObject jsonObj = (JSONObject) obj;
            City cidade = new City(jsonObj.get("city").toString(), state, country);
            cidades.add(cidade);
        }
        return cidades;
    }

    public String constructUrlRequest(String url) throws IOException {
        HttpGet get = new HttpGet(url + "&key=" + PRIVATEKEY);
        CloseableHttpResponse response = this.client.execute(get);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
        else{
            return null;
        }

    }

    public Map<String, Integer> getCacheDetails(){
        HashMap<String, Integer> response = new HashMap<>();
        response.put("hits", this.cache.getHits());
        response.put("misses", this.cache.getMisses());
        response.put("requests", this.cache.getRequests());
        return response;
    }
    

}
