package ma.enset.radarcommandservice;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RadarCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadarCommandServiceApplication.class, args);
    }

    @Bean
    public SimpleCommandBus axonServerCommandBus(){
        return SimpleCommandBus.builder().build();
    }
}
