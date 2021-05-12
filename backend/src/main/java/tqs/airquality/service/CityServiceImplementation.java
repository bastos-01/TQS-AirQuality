package tqs.airquality.service;

import org.apache.http.client.utils.URIBuilder;
import org.apache.tomcat.jni.Poll;
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
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONObject;
import tqs.airquality.entities.Cache;
import tqs.airquality.entities.City;
import tqs.airquality.entities.Pollution;
import tqs.airquality.entities.Weather;

@Service
public class CityServiceImplementation {

    private final String private_key =  "d4e2fea5-a543-41a0-98ac-0d89382a07a1";
    private final String url = "https://api.airvisual.com/v2/";

    private CloseableHttpClient client;

    private Cache cache = new Cache(60);

    public CityServiceImplementation() throws InterruptedException {
        this.client = HttpClients.createDefault();
    }

    public ArrayList<City> getCities(String country, String state) throws IOException, URISyntaxException {
        ArrayList<City> response = handleRequestAllCities(url + "cities?state=" + state + "&country=" + country, country, state);
        return response;
    }

    public ArrayList<String> getStates(String country) throws IOException, URISyntaxException {
        ArrayList<String> response = handleRequestAllStatesOrCountries(url + "states?country=" + country, 0);
        return response;
    }

    public ArrayList<String> getCountries() throws IOException, URISyntaxException {
        ArrayList<String> response = handleRequestAllStatesOrCountries(url + "countries?", 1);
        return response;
    }

    public City getCityData(String country, String state, String city) throws IOException, URISyntaxException {
        City city_data = handleRequestCityData(url + "city?city=" + city +  "&state=" + state + "&country=" + country, country, state, city);
        return city_data;
    }

    public City handleRequestCityData(String url, String country, String state, String city) throws URISyntaxException, IOException {

        City cityFromCache = cache.getCityFromCache(city);

        if(cityFromCache != null){
            return cityFromCache;
        }


        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject response_json = (JSONObject) new JSONObject(response);

        //city data
        JSONObject data = new JSONObject(response_json.get("data").toString());

        //coordinates
        JSONObject location = new JSONObject(data.get("location").toString());
        JSONArray coordinates = new JSONArray(location.get("coordinates").toString());
        Double latitude = Double.parseDouble(coordinates.get(0).toString());
        Double longitude = Double.parseDouble(coordinates.get(0).toString());

        //current
        JSONObject current = new JSONObject(data.get("current").toString());

        //weather
        JSONObject weather_json = new JSONObject(current.get("weather").toString());
        String time_stamp = weather_json.get("ts").toString();
        Double temperature = Double.parseDouble(weather_json.get("tp").toString());
        Double pressure = Double.parseDouble(weather_json.get("pr").toString());
        Double humidity = Double.parseDouble(weather_json.get("hu").toString());
        Double wind_speed = Double.parseDouble(weather_json.get("ws").toString());

        Weather weather = new Weather(temperature, pressure, humidity, wind_speed);

        //pollution
        JSONObject pollution_json = new JSONObject(current.get("pollution").toString());
        Double aqius = Double.parseDouble(pollution_json.get("aqius").toString());
        String mainus = pollution_json.get("mainus").toString();
        Double aqicn = Double.parseDouble(pollution_json.get("aqicn").toString());
        String maincn = pollution_json.get("maincn").toString();

        Pollution pollution = new Pollution(aqius, aqicn, mainus, maincn);

        City full_city = new City(city, state, country, latitude, longitude, time_stamp, weather, pollution);

        cache.addValue(city, full_city);


        return full_city;
    }

    public ArrayList<String> handleRequestAllStatesOrCountries(String url, int flag) throws URISyntaxException, IOException {

        ArrayList<String> values = new ArrayList<>();

        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject response_json = (JSONObject) new JSONObject(response);

        JSONArray data = new JSONArray(response_json.get("data").toString());

        for (Object obj: data){
            JSONObject json_obj = (JSONObject) obj;
            if (flag == 0)
                values.add(json_obj.get("state").toString());
            else
                values.add(json_obj.get("country").toString());

        }
        return values;
    }

    public ArrayList<City> handleRequestAllCities(String url, String country, String state) throws URISyntaxException, IOException {

        ArrayList<City> cidades = new ArrayList<>();

        URIBuilder builder = new URIBuilder(url.replaceAll(" ", "%20"));
        String response = constructUrlRequest(builder.build().toString());

        JSONObject response_json = (JSONObject) new JSONObject(response);

        JSONArray data = new JSONArray(response_json.get("data").toString());

        for (Object obj: data){
            JSONObject json_obj = (JSONObject) obj;
            City cidade = new City(json_obj.get("city").toString(), state, country);
            cidades.add(cidade);
        }
        return cidades;
    }

    public String constructUrlRequest(String url) throws IOException {
        HttpGet get = new HttpGet(url + "&key=" + private_key);
        System.out.println(get);
        CloseableHttpResponse response = this.client.execute(get);

        if (response != null) {
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        }
        else{
            return null;
        }

    }
    

}
