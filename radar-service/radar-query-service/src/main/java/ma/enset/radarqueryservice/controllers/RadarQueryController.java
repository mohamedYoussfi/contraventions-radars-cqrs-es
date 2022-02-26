package ma.enset.radarqueryservice.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import ma.enset.coreapi.*;
import ma.enset.radarqueryservice.entities.Radar;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@CrossOrigin("*")
@RestController
@RequestMapping("/query")
public class RadarQueryController {
    private QueryGateway queryGateway;

    public RadarQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    @GetMapping("/radars/all")
    public CompletableFuture<List<RadarResponseDTO>> allRadars(){
        return queryGateway.query(
                new GetAllRadarsQuery()
                , ResponseTypes.multipleInstancesOf(RadarResponseDTO.class)
        );
    }

    @GetMapping("/radars/byId/{id}")
    public CompletableFuture<RadarOverSpeedsDTO> geRadarById(@PathVariable String id){
        return queryGateway.query(
                new GetRadarById(id)
                , ResponseTypes.instanceOf(RadarOverSpeedsDTO.class)
        );
    }
    @GetMapping("/overSpeeds/all")
    public CompletableFuture<List<OverSpeedResponseDTO>> allOverSpeeds(){
        return queryGateway.query(
                new GetAllOverSpeedsQuery()
                , ResponseTypes.multipleInstancesOf(OverSpeedResponseDTO.class)
        );
    }
    @GetMapping("/overSpeeds/byRegNumber/{regNumber}")
    public CompletableFuture<List<OverSpeedResponseDTO>> ovrSpeedsByRegNumber(@PathVariable String regNumber){
        return queryGateway.query(
                new GetAllOverSpeedsByRegistrationNumberQuery(regNumber)
                , ResponseTypes.multipleInstancesOf(OverSpeedResponseDTO.class)
        );
    }
    @GetMapping(value = "/watchOverSpeeds", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OverSpeedResponseDTO> watch(){
        SubscriptionQueryResult<List<OverSpeedResponseDTO>, OverSpeedResponseDTO> queryResult = this.queryGateway.subscriptionQuery(
                new GetOverSpeedsQuery(),
                ResponseTypes.multipleInstancesOf(OverSpeedResponseDTO.class),
                ResponseTypes.instanceOf(OverSpeedResponseDTO.class)
        );
        return queryResult.initialResult().flatMapMany(Flux::fromIterable).concatWith(queryResult.updates());
    }


    @GetMapping(value = "/subscribeToEvents", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventDataResponseDTO> subscribe(){
        SubscriptionQueryResult<List<EventDataResponseDTO>, EventDataResponseDTO> queryResult = this.queryGateway.subscriptionQuery(
                new SubscribeToEventsQuery(),
                ResponseTypes.multipleInstancesOf(EventDataResponseDTO.class),
                ResponseTypes.instanceOf(EventDataResponseDTO.class)
        );
        return queryResult.initialResult().flatMapMany(Flux::fromIterable).concatWith(queryResult.updates());
    }
}
