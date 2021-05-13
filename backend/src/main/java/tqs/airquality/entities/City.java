package tqs.airquality.entities;

import javax.persistence.*;


@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "timestamp")
    private String timestamp;

    @OneToOne(mappedBy = "city")
    private Weather weather;

    @OneToOne(mappedBy = "city")
    private Pollution pollution;

    public City(String name, String state, String country){
        this.name = name;
        this.state = state;
        this.country = country;
    }

    public City(String name, String state, String country, Double latitude, Double longitude, String timestamp, Weather weather, Pollution pollution) {
        this.name = name;
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.weather = weather;
        this.pollution = pollution;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Weather getWeather() {
        return weather;
    }

    public Pollution getPollution() {
        return pollution;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
