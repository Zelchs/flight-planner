
package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import io.codelex.flightplanner.domain.Flight;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class FlightsRepositoryInMemory {
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
            if (flight.getId().equals(id)) {
                return flight;
            }
        }
        return null;
    }

    public synchronized void deleteFlight(Integer id) {
        flights.removeIf(flight -> flight.getId().equals(id));
    }

    public synchronized List<Airport> getAirports(String search) {
        List<Airport> matchedAirports = new ArrayList<>();
        String keyword = search.toLowerCase().trim();
        for (Flight flight : flights) {
            if (airportContainsText(flight.getFrom(), keyword)) {
                matchedAirports.add(flight.getFrom());
            }
            if (airportContainsText(flight.getTo(), keyword)) {
                matchedAirports.add(flight.getTo());
            }
        }
        return matchedAirports.stream().distinct().toList();
    }

    public synchronized List<Flight> searchFlights(SearchFlightsRequest request) {
        List<Flight> foundFlights = new ArrayList<>();

        for (Flight flight : flights) {
            if (
                    flight.getFrom().getAirport().equals(request.getFrom())
                            && flight.getTo().getAirport().equals(request.getTo())
                            && flight.getDepartureTime().toLocalDate().equals(request.getDepartureDate())
            ) {
                foundFlights.add(flight);
            }
        }
        return foundFlights;
    }

    private boolean airportContainsText(Airport airport, String text) {
        return airport.getCity().toLowerCase().contains(text)
                || airport.getCountry().toLowerCase().contains(text)
                || airport.getAirport().toLowerCase().contains(text);
    }

}
