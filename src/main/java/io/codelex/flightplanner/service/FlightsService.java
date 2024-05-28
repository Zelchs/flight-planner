package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface FlightsService {

    Optional<Flight> add(Flight flight);

    void clear();

    Integer getNewId();

    Flight getFlightById(Integer id);

    void deleteFlight(Integer id);

    List<Airport> getAirports(String search);

    List<Flight> searchFlights(SearchFlightsRequest request);

}
