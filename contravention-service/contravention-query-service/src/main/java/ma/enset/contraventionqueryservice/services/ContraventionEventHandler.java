package ma.enset.contraventionqueryservice.services;
import lombok.extern.slf4j.Slf4j;
import ma.enset.contraventionqueryservice.entities.Contravention;
import ma.enset.contraventionqueryservice.repositories.ContraventionRepository;
import ma.enset.coreapi.ContraventionCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
public class ContraventionEventHandler {
    private ContraventionRepository contraventionRepository;

    public ContraventionEventHandler(ContraventionRepository contraventionRepository) {
        this.contraventionRepository = contraventionRepository;
    }
    @EventHandler
    public void on(ContraventionCreatedEvent event){
        log.info("############ Contravention Query Side ################");
        log.info("ContraventionCreatedEvent occured....");
        Contravention contravention=new Contravention();
        contravention.setContraventionId(event.getId());
        contravention.setAmount(event.getPayload().getAmount());
        contravention.setInstant(event.getPayload().getTimeStamp());
        contravention.setOwnerAddress(event.getPayload().getOwnerAddress());
        contravention.setOwnerEmail(event.getPayload().getOwnerEmail());
        contravention.setOwnerNationalCardId(event.getPayload().getOwnerNationalCardId());
        contravention.setRadarAlt(event.getPayload().getRadarAltitude());
        contravention.setRadarLong(event.getPayload().getRadarLongitude());
        contravention.setRadarAlt(event.getPayload().getRadarAltitude());
        contravention.setOwnerPhoneNumber(event.getPayload().getOwnerPhoneNumber());
        contravention.setRadarId(event.getPayload().getRadarId());
        contravention.setRadarMaxSpeed(event.getPayload().getRadarMaxSpeed());
        contravention.setStatus(event.getPayload().getStatus());
        contravention.setVehicleOwner(event.getPayload().getVehicleOwner());
        contravention.setVehicleRegistrationNumber(event.getPayload().getVehicleRegistrationNumber());
        contravention.setVehicleType(event.getPayload().getVehicleType());
        contravention.setVehicleSpeed(event.getPayload().getVehicleSpeed());
        contraventionRepository.save(contravention);
    }

}
