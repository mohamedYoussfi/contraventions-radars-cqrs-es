package ma.enset.contraventionqueryservice.repositories;

import ma.enset.contraventionqueryservice.entities.Contravention;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContraventionRepository extends JpaRepository<Contravention,String> {
    List<Contravention> findAllByOwnerNationalCardId(String ncID);
}
