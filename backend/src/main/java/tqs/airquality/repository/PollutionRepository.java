package tqs.airquality.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.airquality.entities.City;

@Repository
public interface PollutionRepository extends JpaRepository<City, Long>{
}
