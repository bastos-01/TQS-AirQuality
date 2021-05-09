package tqs.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "pollution")
@EntityListeners(AuditingEntityListener.class)
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
    private Double main_poll_us;

    @Column(name = "main_poll_cn")
    private Double main_poll_cn;

}
