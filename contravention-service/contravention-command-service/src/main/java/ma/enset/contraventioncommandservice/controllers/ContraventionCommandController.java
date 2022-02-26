package ma.enset.contraventioncommandservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@RestController
@Slf4j
@RequestMapping("/commands")
@CrossOrigin("*")
public class ContraventionCommandController {
    private EventStore eventStore;

    public ContraventionCommandController(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @GetMapping("/events/{contraventionId}")
    public Stream eventsStream(@PathVariable String contraventionId){
        return eventStore.readEvents(contraventionId).asStream();
    }
}
