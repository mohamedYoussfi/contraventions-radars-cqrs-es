package ma.enset.contraventioncommandservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContraventionCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContraventionCommandServiceApplication.class, args);
    }
    @Bean
    CommandBus commandBus(){
        return SimpleCommandBus.builder().build();
    }


}
