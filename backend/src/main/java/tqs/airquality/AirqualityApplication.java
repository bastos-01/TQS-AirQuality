package tqs.airquality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tqs.airquality.controller.CityRestController;

@SpringBootApplication
public class AirqualityApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirqualityApplication.class, args);
    }

}
