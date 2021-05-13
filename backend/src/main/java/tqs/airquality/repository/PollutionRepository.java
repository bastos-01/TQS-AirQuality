package tqs.airquality.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.airquality.entities.Pollution;

@Repository
public interface PollutionRepository extends JpaRepository<Pollution, Long>{
}
