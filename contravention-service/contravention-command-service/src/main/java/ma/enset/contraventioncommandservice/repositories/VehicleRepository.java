package ma.enset.contraventioncommandservice.repositories;

import ma.enset.contraventioncommandservice.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle,String> {
}
