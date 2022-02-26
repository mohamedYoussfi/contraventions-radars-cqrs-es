package ma.enset.radarcommandservice.controllers;

import lombok.extern.slf4j.Slf4j;
import ma.enset.coreapi.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PrinterMakeAndModel;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@Slf4j
@RequestMapping("/commands")
@CrossOrigin("*")
public class RadarCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    public RadarCommandController(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }
    @PostMapping("/radar/create")
    public CompletableFuture<String> addNewRadarCommand(@RequestBody RadarDTO request){
        return this.commandGateway.send(new CreateNewRadarCommand(
                UUID.randomUUID().toString(),
                request
        ));
    }
    @GetMapping("/eventStore/{id}")
    public Stream streamEvents(@PathVariable String id){
        return eventStore.readEvents(id).asStream();
    }

    @PostMapping("/radar/changeStatus")
    public CompletableFuture<String> changeRadarStatus(@RequestBody ChangeRadarStatusRequestDTO request){
        return this.commandGateway.send(new ChangeRadarStatusCommand(
                request.getRadarId(),
                request.getRadarStatus()
        ));
    }

    @PostMapping("/radar/changeSpeedLimit")
    public CompletableFuture<String> changeSpeedLimit(@RequestBody ChangeRadarSpeedLimitRequestDTO request){
        return this.commandGateway.send(new ChangeRadarSpeedLimitCommand(
                request.getRadarId(),
                request.getSpeed()
        ));
    }
    @PostMapping("/radar/overSpeed")
    public CompletableFuture<String> newOverSpeed(@RequestBody OverSpeedRequestDTO request){
        CompletableFuture<String> cmd1 = this.commandGateway.send(new NewVehicleOverSpeedDetectionCommand(
                request.getRadarId(),
                request
        ));
        return cmd1;
    }

}
