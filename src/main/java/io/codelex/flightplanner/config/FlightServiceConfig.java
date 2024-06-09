package io.codelex.flightplanner.config;

import io.codelex.flightplanner.repository.FlightRepositoryDB;
import io.codelex.flightplanner.repository.AirportRepository;
import io.codelex.flightplanner.repository.FlightsRepositoryInMemory;
import io.codelex.flightplanner.service.FlightsService;
import io.codelex.flightplanner.service.FlightsServiceDB;
import io.codelex.flightplanner.service.FlightsServiceInMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlightServiceConfig {
    @Bean
    @Profile("in-memory")
    public FlightsService flightServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory) {
        return new FlightsServiceInMemory(flightsRepositoryInMemory);
    }

    @Bean
    @Profile("db")
    public FlightsService flightServiceDB(FlightRepositoryDB flightRepositoryDB, AirportRepository airportRepository) {
        return new FlightsServiceDB(flightRepositoryDB, airportRepository);
    }
}
