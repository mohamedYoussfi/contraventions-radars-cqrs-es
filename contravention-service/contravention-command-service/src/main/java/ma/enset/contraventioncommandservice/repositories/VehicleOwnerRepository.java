package ma.enset.contraventioncommandservice.repositories;
import ma.enset.contraventioncommandservice.model.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner,String> {
}
