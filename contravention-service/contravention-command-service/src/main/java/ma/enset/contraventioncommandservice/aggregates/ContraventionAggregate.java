package ma.enset.contraventioncommandservice.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.enset.contraventioncommandservice.model.Vehicle;
import ma.enset.contraventioncommandservice.repositories.VehicleOwnerRepository;
import ma.enset.contraventioncommandservice.repositories.VehicleRepository;
import ma.enset.coreapi.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
@Slf4j
public class ContraventionAggregate {
    @AggregateIdentifier
    private String contraventionId;
    private ContraventionData contraventionData;
    @CommandHandler
    public ContraventionAggregate(NewContraventionCommand command, VehicleRepository vehicleRepository, VehicleOwnerRepository vehicleOwnerRepository) {
        log.info("===========================================");
        log.info("+++++ NewContraventionCommand received");
            ContraventionData contraventionData=new ContraventionData();
            contraventionData.setContraventionId(UUID.randomUUID().toString());
            contraventionData.setOverSpeedId(command.getPayload().getOverSpeedId());
            int radarMaxSpeed = command.getPayload().getRadarMaxSpeed();
            int vehicleSpeed=command.getPayload().getVehicleSpeed();
            double amount=0;
            if((vehicleSpeed-radarMaxSpeed)>100){
                amount=4000;
            } else if((vehicleSpeed-radarMaxSpeed)>50){
                amount=3000;
            }else if((vehicleSpeed-radarMaxSpeed)>20){
                amount=1000;
            }else if((vehicleSpeed-radarMaxSpeed)>10){
                amount=500;
            }
            contraventionData.setAmount(amount);
            contraventionData.setVehicleSpeed(command.getPayload().getVehicleSpeed());
            contraventionData.setRadarId(command.getPayload().getRadarId());
            contraventionData.setTimeStamp(command.getPayload().getTimeStamp());
            contraventionData.setVehicleRegistrationNumber(command.getPayload().getVehicleRegistrationNumber());
            Vehicle vehicle=vehicleRepository.findById(command.getPayload().getVehicleRegistrationNumber())
                    .orElse(null);
            log.info("===============================================");
            log.info(command.getPayload().getVehicleRegistrationNumber());
            log.info("=>"+vehicle);
            if(vehicle!=null){
                contraventionData.setVehicleOwner(vehicle.getVehicleOwner().getName());
                contraventionData.setOwnerEmail(vehicle.getVehicleOwner().getEmail());
                contraventionData.setOwnerAddress(vehicle.getVehicleOwner().getAddress());
                contraventionData.setOwnerNationalCardId(vehicle.getVehicleOwner().getNationalCardNumber());
                contraventionData.setOwnerPhoneNumber(vehicle.getVehicleOwner().getPhoneNumber());
                contraventionData.setVehicleType(vehicle.getType());
                contraventionData.setStatus(ContraventionStatus.VALIDATED);
            }
            AggregateLifecycle.apply(new ContraventionCreatedEvent(
                    command.getId(),
                    contraventionData
            ));
    }
    @EventSourcingHandler
    public void on(ContraventionCreatedEvent event){
        log.info("===========================================");
        log.info("+++++ ContraventionCreatedEvent occured");
        this.contraventionId=event.getId();
        this.contraventionData=event.getPayload();
        log.info("=>=>:"+this.contraventionId);
    }
}
