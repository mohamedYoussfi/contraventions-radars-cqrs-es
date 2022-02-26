package ma.enset.contraventionqueryservice.services;

import lombok.extern.slf4j.Slf4j;
import ma.enset.contraventionqueryservice.entities.Contravention;
import ma.enset.contraventionqueryservice.repositories.ContraventionRepository;
import ma.enset.coreapi.GetAllContraventions;
import ma.enset.coreapi.GetContraventionsByNationalCardNumber;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ContraventionQueryHandler {
    private ContraventionRepository contraventionRepository;

    public ContraventionQueryHandler(ContraventionRepository contraventionRepository) {
        this.contraventionRepository = contraventionRepository;
    }
    @QueryHandler
    public List<Contravention> on(GetAllContraventions query){
        return contraventionRepository.findAll();
    }
    @QueryHandler
    public List<Contravention> on(GetContraventionsByNationalCardNumber query){
        return contraventionRepository.findAllByOwnerNationalCardId(query.getNationalCardNumber());
    }
}
