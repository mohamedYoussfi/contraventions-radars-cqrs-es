package ma.enset.radarqueryservice.services;

import io.axoniq.axonserver.grpc.event.Event;
import lombok.extern.slf4j.Slf4j;
import ma.enset.coreapi.*;
import ma.enset.radarqueryservice.entities.OverSpeedDetection;
import ma.enset.radarqueryservice.entities.Radar;
import ma.enset.radarqueryservice.mappers.RadarMappers;
import ma.enset.radarqueryservice.repositories.OverSpeedDetectionRepository;
import ma.enset.radarqueryservice.repositories.RadarRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.messaging.Message;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class RadarEventHadlerService {
    private RadarRepository radarRepository;
    private OverSpeedDetectionRepository overSpeedDetectionRepository;
    private RadarMappers radarMappers;
    private QueryUpdateEmitter queryUpdateEmitter;


    public RadarEventHadlerService(RadarRepository radarRepository, OverSpeedDetectionRepository overSpeedDetectionRepository, RadarMappers radarMappers, QueryUpdateEmitter queryUpdateEmitter) {
        this.radarRepository = radarRepository;
        this.overSpeedDetectionRepository = overSpeedDetectionRepository;
        this.radarMappers = radarMappers;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }

    @EventHandler
    public void on(RadarCreatedEvent event, EventMessage<RadarCreatedEvent> eventMessage){
        log.info("*************** Query Event handler **************");
        log.info("RadarCreatedEvent occured");
        Radar radar=radarMappers.from(event.getPayload());
        radar.setRadarId(event.getId());
        radarRepository.save(radar);
        event.getPayload().setRadarId(event.getId());
        EventDataResponseDTO eventDataResponseDTO=new EventDataResponseDTO(
                event.getClass().getSimpleName(),
                event
        );
        queryUpdateEmitter.emit(SubscribeToEventsQuery.class,
                (query)->true, eventDataResponseDTO);

    }
    @EventHandler
    public void on(RadarStatusChangedEvent event){
        log.info("*************** Query Event handler **************");
        log.info("RadarCreatedEvent occured");
        Radar radar=radarRepository.findById(event.getId())
                .orElseThrow(()->new RuntimeException("Radar not found"));
        radar.setRadarStatus(event.getPayload());
        radarRepository.save(radar);
        EventDataResponseDTO eventDataResponseDTO=new EventDataResponseDTO(
                event.getClass().getSimpleName(),
                event
        );
        queryUpdateEmitter.emit(SubscribeToEventsQuery.class,
                (query)->true, eventDataResponseDTO);
    }
    @EventHandler
    public void on(RadarSpeedLimitChangedEvent event){
        log.info("*************** Query Event handler **************");
        log.info("RadarCreatedEvent occured");
        Radar radar=radarRepository.findById(event.getId())
                .orElseThrow(()->new RuntimeException("Radar not found"));
        radar.setMaxSpeed(event.getPayload());
        radarRepository.save(radar);
        EventDataResponseDTO eventDataResponseDTO=new EventDataResponseDTO(
                event.getClass().getSimpleName(),
                event
        );
        queryUpdateEmitter.emit(SubscribeToEventsQuery.class,
                (query)->true, eventDataResponseDTO);
    }

    @EventHandler
    public void on(VehicleOverSpeedDetectedEvent event, @Timestamp Instant instant){
        log.info("*************** Query Event handler **************");
        log.info("VehicleOverSpeedDetectedEvent occured");
        OverSpeedDetection overSpeedDetection=radarMappers.from(event.getPayload());
        String radarId=event.getId();
        Radar radar=radarRepository.findById(radarId)
                .orElseThrow(()->new RuntimeException("Radar not found"));
        overSpeedDetection.setRadar(radar);
        log.info("**********--********=> "+overSpeedDetection.getOverSpeedId());
        if(overSpeedDetection.getOverSpeedId()==null){
            overSpeedDetection.setOverSpeedId(UUID.randomUUID().toString());
        }
        overSpeedDetection.setTimeStamp(instant);
        OverSpeedDetection savedOverSpeedDetection = overSpeedDetectionRepository.save(overSpeedDetection);
        OverSpeedResponseDTO responseDTO = radarMappers.fromOS(savedOverSpeedDetection);
        /*
        log.info("*** Emit => "+responseDTO);
        queryUpdateEmitter.emit(
                GetOverSpeedsQuery.class,
                (query)->true,responseDTO );

         */
        event.getPayload().setTimeStamp(instant);
        EventDataResponseDTO eventDataResponseDTO=new EventDataResponseDTO(
                event.getClass().getSimpleName(),
                event
        );
        queryUpdateEmitter.emit(SubscribeToEventsQuery.class,
                (query)->true, eventDataResponseDTO);
    }

}
