package ma.enset.contraventionqueryservice.controller;

import ma.enset.contraventionqueryservice.entities.Contravention;
import ma.enset.coreapi.GetAllContraventions;
import ma.enset.coreapi.GetContraventionsByNationalCardNumber;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query")
@CrossOrigin("*")
public class ContraventionQueryController {
    private QueryGateway queryGateway;

    public ContraventionQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/contraventions/all")
    public CompletableFuture<List<Contravention>> allContraventions(){
        return queryGateway.query(
                new GetAllContraventions(),
                ResponseTypes.multipleInstancesOf(Contravention.class)
        );
    }
    @GetMapping("/contraventions/byNationalCardId/{natCardNumber}")
    public CompletableFuture<List<Contravention>> contravrntionByNationalCardNumber(@PathVariable String natCardNumber){
        return queryGateway.query(
                new GetContraventionsByNationalCardNumber(natCardNumber),
                ResponseTypes.multipleInstancesOf(Contravention.class)
        );
    }
}
