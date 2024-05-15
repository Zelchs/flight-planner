package io.codelex.flightplanner.adminapi;

import io.codelex.flightplanner.airports.Airport;
import io.codelex.flightplanner.clientapi.SearchFlightsRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class FlightsRepository {
    private final Set<Flight> flights = new HashSet<>();

    public synchronized Flight add(Flight flight) {
        if (flights.add(flight)) {
            return flight;
        }
        return null;
    }

    public synchronized void clear() {
        flights.clear();
    }


    public synchronized Flight getFlightById(Integer id) {
        for (Flight flight : flights) {
            if (flight.id().equals(id)) {
                return flight;
            }
        }
        return null;
    }

    public synchronized void deleteFlight(Integer id) {
        flights.removeIf(flight -> flight.id().equals(id));
    }

    public synchronized List<Airport> getAirports(String search) {
        List<Airport> matchedAirports = new ArrayList<>();
        String keyword = search.toLowerCase().trim();
        for (Flight flight : flights) {
            if (flight.from().city().toLowerCase().contains(keyword)
                    || flight.from().country().toLowerCase().contains(keyword)
                    || flight.from().airport().toLowerCase().contains(keyword)) {
                matchedAirports.add(flight.from());
            }
            if (flight.to().city().toLowerCase().contains(keyword)
                    || flight.to().country().toLowerCase().contains(keyword)
                    || flight.to().airport().toLowerCase().contains(keyword)) {
                matchedAirports.add(flight.to());
            }
        }
        return matchedAirports.stream().distinct().toList();
    }

    public synchronized List<Flight> searchFlights(SearchFlightsRequest request) {
        List<Flight> foundFlights = new ArrayList<>();

        for (Flight flight : flights) {
            if (
                    flight.from().airport().equals(request.getFrom())
                            && flight.to().airport().equals(request.getTo())
                            && flight.departureTime().toLocalDate().equals(request.getDepartureDate())
            ) {
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }

}
