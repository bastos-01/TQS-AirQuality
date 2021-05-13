package tqs.airquality.entities;

import javax.persistence.*;


@Entity
@Table(name = "pollution")
public class Pollution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "city")
    private City city;

    @Column(name = "aqiUs")
    private Double aqiUs;

    @Column(name = "aqiCn")
    private Double aqiCn;

    @Column(name = "mainPollUs")
    private String mainPollUs;

    @Column(name = "mainPollCn")
    private String mainPollCn;

    public Pollution(Double aqiUs, Double aqiCn, String mainPollUs, String mainPollCn) {
        this.aqiUs = aqiUs;
        this.aqiCn = aqiCn;
        this.mainPollUs = mainPollUs;
        this.mainPollCn = mainPollCn;
    }

    public Double getAqiUs() {
        return aqiUs;
    }

    public Double getAqiCn() {
        return aqiCn;
    }

    public String getMainPollUs() {
        return mainPollUs;
    }

    public String getMainPollCn() {
        return mainPollCn;
    }
}
