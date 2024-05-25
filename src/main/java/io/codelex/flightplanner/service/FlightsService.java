package io.codelex.flightplanner.service;

import io.codelex.flightplanner.repository.FlightsRepository;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import io.codelex.flightplanner.domain.Flight;
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
