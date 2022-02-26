package ma.enset.radarqueryservice;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RadarQueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RadarQueryServiceApplication.class, args);
	}
}
