package com.example.immatriculationcommandservice.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.enset.coreapi.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class VehicleRegistrationCenterAggregate {
    @AggregateIdentifier
    private String registrationNumber;
    private VehicleType type;
    private String brand;
    private String model;
    private int fiscalPower;
    private String ownerName;
    private String ownerNationalIdCard;
    private String ownerEmail;
    private String ownerPhoneNumber;
    private String ownerAddress;

    public VehicleRegistrationCenterAggregate() {
    }
    @CommandHandler
    public VehicleRegistrationCenterAggregate(CreateVehicleCommand command) {
        log.info("CreateVehicleCommand received ");
        AggregateLifecycle.apply(new VehicleCreatedEvent(
                command.getId(),
                command.getPayload()
        ));
    }
    @EventSourcingHandler
    public void on(VehicleCreatedEvent event) {
        log.info("VehicleCreatedEvent occuered");
        this.registrationNumber=event.getId();
        this.brand=event.getPayload().getBrand();
        this.fiscalPower=event.getPayload().getFiscalPower();
        this.model=event.getPayload().getModel();
        this.type=event.getPayload().getType();
        this.ownerName=event.getPayload().getOwnerName();
        this.ownerEmail=event.getPayload().getOwnerEmail();
        this.ownerAddress=event.getPayload().getOwnerAddress();
        this.ownerNationalIdCard=event.getPayload().getOwnerNationalIdCard();
        this.ownerPhoneNumber=event.getPayload().getOwnerPhoneNumber();
    }
    @CommandHandler
    public void handle(UpdateVehicleOwnerCommand command){
        log.info("UpdateVehicleOwnerCommand received");
        if(this.ownerNationalIdCard.equals(command.getPayload().getOwnerNationalIdCard()))
            throw new VehicleOwnerAlreadyAffctedException("This Owner is already affected to this vehicle");
        AggregateLifecycle.apply(new VehicleOwnerUpdatedEvent(
                command.getId(),
                command.getPayload()
        ));
    }

    @EventSourcingHandler
    public void on(VehicleOwnerUpdatedEvent event) {
        log.info("VehicleOwnerUpdatedEvent occuered");
        this.registrationNumber=event.getId();
        this.ownerName=event.getPayload().getOwnerName();
        this.ownerEmail=event.getPayload().getOwnerEmail();
        this.ownerAddress=event.getPayload().getOwnerAddress();
        this.ownerNationalIdCard=event.getPayload().getOwnerNationalIdCard();
        this.ownerPhoneNumber=event.getPayload().getOwnerPhoneNumber();
    }
}
