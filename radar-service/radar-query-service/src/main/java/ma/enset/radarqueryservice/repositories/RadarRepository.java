package ma.enset.radarqueryservice.repositories;

import ma.enset.radarqueryservice.entities.Radar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RadarRepository extends JpaRepository<Radar,String> {
}
