package ma.enset.radarcommandservice.aggregates;
import lombok.extern.slf4j.Slf4j;
import ma.enset.coreapi.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Aggregate
@Slf4j
public class RadarAggregate {
    @AggregateIdentifier
    private String radarId;
    private String name;
    private double longitude;
    private double latitude;
    private  double altitude;
    private int maxSpeed;
    private String roadDesignation;
    private RadarStatus radarStatus;

    @AggregateMember
    private List<OverSpeedMember> overSpeedMembers =new ArrayList<>();

    public RadarAggregate() {
    }
    @CommandHandler
    public RadarAggregate(CreateNewRadarCommand command) {
        log.info("CreateNewRadarCommand received");
        AggregateLifecycle.apply(new RadarCreatedEvent(
                command.getId(),
                command.getPayload()
        ));
    }
    @EventSourcingHandler
    public void on(RadarCreatedEvent event){
        log.info("RadarCreatedEvent occured");
        this.radarId=event.getId();
        this.name=event.getPayload().getName();
        this.altitude=event.getPayload().getAltitude();
        this.longitude=event.getPayload().getLongitude();
        this.latitude=event.getPayload().getLatitude();
        this.radarStatus=event.getPayload().getRadarStatus();
        this.maxSpeed=event.getPayload().getMaxSpeed();
        this.roadDesignation=event.getPayload().getRoadDesignation();
    }
    @CommandHandler
    public void handle(ChangeRadarStatusCommand command){
        log.info("ChangeRadarStatusCommand received");
        AggregateLifecycle.apply(new RadarStatusChangedEvent(
           command.getId(),
           command.getPayload()
        ));
    }
    @EventSourcingHandler
    public void on(RadarStatusChangedEvent event){
        log.info("RadarStatusChangedEvent to"+ event.getPayload()+" Occured");
        this.radarId=event.getId();
        this.radarStatus=event.getPayload();
    }

    @CommandHandler
    public void handle(ChangeRadarSpeedLimitCommand command){
        log.info("ChangeRadarSpeedLimitCommand received");
        AggregateLifecycle.apply(new RadarSpeedLimitChangedEvent(
                command.getId(),
                command.getPayload()
        ));
    }
    @EventSourcingHandler
    public void on(RadarSpeedLimitChangedEvent event){
        log.info("RadarSpeedLimitChangedEvent to"+ event.getPayload()+" Occured");
        this.radarId=event.getId();
        this.maxSpeed=event.getPayload();
    }

    @CommandHandler
    public void handle(NewVehicleOverSpeedDetectionCommand command){
        log.info("NewVehicleOversSpeedDetectionCommand received");
        command.getPayload().setOverSpeedId(UUID.randomUUID().toString());
        command.getPayload().setRadarMaxSpeed(this.maxSpeed);
        command.getPayload().setRadarLongitude(this.longitude);
        command.getPayload().setRadarLongitude(this.latitude);
        command.getPayload().setRadarAltitude(this.altitude);
        AggregateLifecycle.apply(new VehicleOverSpeedDetectedEvent(
                command.getId(),
                command.getPayload(),
                UUID.randomUUID().toString()
        ));
    }
    @EventSourcingHandler
    public void on(VehicleOverSpeedDetectedEvent event){
        log.info("VehicleOversSpeedDetectedEvent to Occured");
        this.radarId=event.getId();
        OverSpeedMember overSpeedMember =new OverSpeedMember();
        overSpeedMember.setId(UUID.randomUUID().toString());
        overSpeedMember.setVehicleRegistrationNumber(event.getPayload().getVehicleRegistrationNumber());
        overSpeedMember.setVehicleSpeed(event.getPayload().getVehicleSpeed());
        this.overSpeedMembers.add(overSpeedMember);
        log.info("New OverSpeed added => "+overSpeedMembers.size());
    }
}
