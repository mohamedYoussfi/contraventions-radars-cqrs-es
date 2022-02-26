package com.example.immatriculationcommandservice;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class ImmatriculationCommandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImmatriculationCommandServiceApplication.class, args);
    }
    @Bean
    public CommandBus commandBus(){
        return SimpleCommandBus.builder().build();
    }
}
