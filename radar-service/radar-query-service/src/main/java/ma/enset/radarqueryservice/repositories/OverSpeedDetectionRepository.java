package ma.enset.radarqueryservice.repositories;

import ma.enset.radarqueryservice.entities.OverSpeedDetection;
import ma.enset.radarqueryservice.entities.Radar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OverSpeedDetectionRepository extends JpaRepository<OverSpeedDetection,String> {
    List<OverSpeedDetection> findByVehicleRegistrationNumber(String regNumber);
}
