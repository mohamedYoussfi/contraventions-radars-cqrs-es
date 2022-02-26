package ma.enset.contraventioncommandservice.services;

import lombok.extern.slf4j.Slf4j;
import ma.enset.contraventioncommandservice.model.Vehicle;
import ma.enset.contraventioncommandservice.model.VehicleOwner;
import ma.enset.contraventioncommandservice.repositories.VehicleOwnerRepository;
import ma.enset.contraventioncommandservice.repositories.VehicleRepository;
import ma.enset.coreapi.NewContraventionCommand;
import ma.enset.coreapi.VehicleCreatedEvent;
import ma.enset.coreapi.VehicleOverSpeedDetectedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.DomainEventMessage;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContraventionServiceHandler {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    private VehicleRepository vehicleRepository;
    private VehicleOwnerRepository vehicleOwnerRepository;

    public ContraventionServiceHandler(CommandGateway commandGateway, EventStore eventStore, VehicleRepository vehicleRepository, VehicleOwnerRepository vehicleOwnerRepository) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
        this.vehicleRepository = vehicleRepository;
        this.vehicleOwnerRepository = vehicleOwnerRepository;
    }


    @EventHandler
    public void on(VehicleOverSpeedDetectedEvent event){
        log.info("+++++VehicleOverSpeedDetectedEvent occured");
        if(event.getContraventionId()==null) return;
        List<? extends DomainEventMessage<?>> domainEventMessages=new ArrayList<>();
        DomainEventStream domainEventStream = eventStore.readEvents(event.getContraventionId());
        domainEventMessages = domainEventStream.asStream().collect(Collectors.toList());
        if(domainEventMessages.size()==0){
            commandGateway.send(new NewContraventionCommand(
                    event.getContraventionId(),
                    event.getPayload()
            ));
        }
    }
    @EventHandler
    public void on(VehicleCreatedEvent event){
        log.info("VehicleCreatedEvent occured");
        Vehicle vehicle=new Vehicle();
        vehicle.setRegistrationNumber(event.getPayload().getRegistrationNumber());
        vehicle.setModel(event.getPayload().getModel());
        vehicle.setFiscalPower(event.getPayload().getFiscalPower());
        vehicle.setType(event.getPayload().getType());
        VehicleOwner vehicleOwner=new VehicleOwner();
        vehicleOwner.setName(event.getPayload().getOwnerName());
        vehicleOwner.setId(UUID.randomUUID().toString());
        vehicleOwner.setPhoneNumber(event.getPayload().getOwnerPhoneNumber());
        vehicleOwner.setEmail(event.getPayload().getOwnerEmail());
        vehicleOwner.setNationalCardNumber(event.getPayload().getOwnerNationalIdCard());
        vehicleOwner.setAddress(event.getPayload().getOwnerAddress());
        VehicleOwner savedVehicleOwner = vehicleOwnerRepository.save(vehicleOwner);
        vehicle.setVehicleOwner(savedVehicleOwner);
        vehicleRepository.save(vehicle);
    }
}
