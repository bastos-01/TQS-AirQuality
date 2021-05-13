package tqs.airquality.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CityTests {

    @Test
    void getsTest(){
        Weather weather = new Weather(12.0, 100.2, 20.0, 3.0);
        Pollution pollution = new Pollution(12.0, 10.2, "p2", "p2");
        City cidade = new City("Aveiro", "Aveiro", "Portugal", 36.56, 29.12, "timestamp",weather, pollution );

        assertEquals("Aveiro", cidade.getName());
        assertEquals("Aveiro", cidade.getState());
        assertEquals("Portugal", cidade.getCountry());
        assertEquals(36.56, cidade.getLatitude());
        assertEquals(29.12, cidade.getLongitude());
        assertEquals("timestamp", cidade.getTimestamp());
        assertEquals(weather, cidade.getWeather());
        assertEquals(pollution, cidade.getPollution());

        assertEquals(12.0, weather.getTemperature());
        assertEquals(100.2, weather.getPressure());
        assertEquals(20.0, weather.getHumidity());
        assertEquals(3.0, weather.getWindSpeed());

        assertEquals(12.0, pollution.getAqiUs());
        assertEquals(10.2, pollution.getAqiCn());
        assertEquals("p2", pollution.getMainPollUs());
        assertEquals("p2", pollution.getMainPollCn());

    }
}
