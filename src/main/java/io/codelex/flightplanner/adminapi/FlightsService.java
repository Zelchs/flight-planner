package io.codelex.flightplanner.adminapi;

import io.codelex.flightplanner.airports.Airport;
import io.codelex.flightplanner.clientapi.SearchFlightsRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FlightsService {

    private final AtomicInteger id = new AtomicInteger(0);


    FlightsRepository flightsRepository;

    public FlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public Optional<Flight> add(Flight flight) {
        return Optional.ofNullable(flightsRepository.add(flight));
    }

    public void clear() {
        flightsRepository.clear();
    }

    public Integer getNewId() {
        return id.incrementAndGet();
    }

    public Flight getFlightById(Integer id) {
        return flightsRepository.getFlightById(id);
    }

    public void deleteFlight(Integer id) {
        flightsRepository.deleteFlight(id);
    }

    public List<Airport> getAirports(String search) {
        return flightsRepository.getAirports(search);
    }

    public List<Flight> searchFlights(SearchFlightsRequest request) {
        return flightsRepository.searchFlights(request);
    }
}
