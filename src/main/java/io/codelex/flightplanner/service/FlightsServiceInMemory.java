package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.FlightsRepositoryInMemory;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("in-memory")
public class FlightsServiceInMemory implements FlightsService {

    private final AtomicInteger id = new AtomicInteger(0);

    FlightsRepositoryInMemory flightsRepositoryInMemory;

    public FlightsServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory) {
        this.flightsRepositoryInMemory = flightsRepositoryInMemory;
    }

    public Optional<Flight> add(Flight flight) {
        flight.setId(id.incrementAndGet());
        return Optional.ofNullable(flightsRepositoryInMemory.add(flight));
    }

    public void clear() {
        flightsRepositoryInMemory.clear();
    }

    public Flight getFlightById(Integer id) {
        return flightsRepositoryInMemory.getFlightById(id);
    }

    public void deleteFlight(Integer id) {
        flightsRepositoryInMemory.deleteFlight(id);
    }

    public List<Airport> getAirports(String search) {
        return flightsRepositoryInMemory.getAirports(search);
    }

    public List<Flight> searchFlights(SearchFlightsRequest request) {
        return flightsRepositoryInMemory.searchFlights(request);
    }
}
