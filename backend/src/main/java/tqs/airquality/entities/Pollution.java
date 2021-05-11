package tqs.airquality.entities;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "pollution")
public class Pollution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "city")
    private City city;

    @Column(name = "aqi_us")
    private Double aqi_us;

    @Column(name = "aqi_cn")
    private Double aqui_cn;

    @Column(name = "main_poll_us")
    private String main_poll_us;

    @Column(name = "main_poll_cn")
    private String main_poll_cn;

    public Pollution(Double aqi_us, Double aqui_cn, String main_poll_us, String main_poll_cn) {
        this.aqi_us = aqi_us;
        this.aqui_cn = aqui_cn;
        this.main_poll_us = main_poll_us;
        this.main_poll_cn = main_poll_cn;
    }

    public Double getAqi_us() {
        return aqi_us;
    }

    public void setAqi_us(Double aqi_us) {
        this.aqi_us = aqi_us;
    }

    public Double getAqui_cn() {
        return aqui_cn;
    }

    public void setAqui_cn(Double aqui_cn) {
        this.aqui_cn = aqui_cn;
    }

    public String getMain_poll_us() {
        return main_poll_us;
    }

    public void setMain_poll_us(String main_poll_us) {
        this.main_poll_us = main_poll_us;
    }

    public String getMain_poll_cn() {
        return main_poll_cn;
    }

    public void setMain_poll_cn(String main_poll_cn) {
        this.main_poll_cn = main_poll_cn;
    }
}
