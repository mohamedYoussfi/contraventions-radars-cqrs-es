package ma.enset.contraventionqueryservice.controller;

import ma.enset.contraventionqueryservice.entities.Contravention;
import ma.enset.coreapi.GetAllContraventions;
import ma.enset.coreapi.GetContraventionsByNationalCardNumber;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.data.domain.Page;
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


    @GetMapping("/contraventions")
    public CompletableFuture<Page> contravrntionByNationalCardNumber(
                                                                     @RequestParam(name = "page",defaultValue = "0") int page,
                                                                     @RequestParam(name = "size",defaultValue = "10") int size,
                                                                     @RequestParam(name = "ncid") String natCardNumber){
        return queryGateway.query(
                new GetContraventionsByNationalCardNumber(natCardNumber,page,size),
                ResponseTypes.instanceOf(Page.class)
        );
    }
}
