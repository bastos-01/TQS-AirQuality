package tqs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import tqs.entities.City;

@Repository
public interface PollutionRepository extends JpaRepository<City, Long>{
}
