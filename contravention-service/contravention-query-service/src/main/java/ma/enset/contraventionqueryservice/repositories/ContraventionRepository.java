package ma.enset.contraventionqueryservice.repositories;

import ma.enset.contraventionqueryservice.entities.Contravention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContraventionRepository extends JpaRepository<Contravention,String> {
    Page<Contravention> findAllByOwnerNationalCardId(String ncID, Pageable pageable);
}
