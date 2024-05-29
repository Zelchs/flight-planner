package io.codelex.flightplanner.service;

import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.repository.AirportRepository;
import io.codelex.flightplanner.repository.FlightRepositoryDB;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("db")
public class FlightsServiceDB implements FlightsService {

    private final AtomicInteger id = new AtomicInteger(0);
    private final FlightRepositoryDB flightRepositoryDB;
    private final AirportRepository airportRepository;

    public FlightsServiceDB(FlightRepositoryDB flightRepositoryDB, AirportRepository airportRepository) {
        this.flightRepositoryDB = flightRepositoryDB;
        this.airportRepository = airportRepository;
    }

    @Override
    @Transactional
    public Optional<Flight> add(Flight flight) {
        Airport fromAirport = findOrCreateAirport(flight.getFrom());
        Airport toAirport = findOrCreateAirport(flight.getTo());

        flight.setFrom(fromAirport);
        flight.setTo(toAirport);

        List<Flight> existingFlights = flightRepositoryDB.findByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(
                fromAirport.getAirport(),
                toAirport.getAirport(),
                flight.getCarrier(),
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );

        if (!existingFlights.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(flightRepositoryDB.save(flight));
        } catch (DataIntegrityViolationException | OptimisticLockingFailureException e) {
            return Optional.empty();
        }
    }

    @Override
    public void clear() {
        flightRepositoryDB.deleteAll();
        airportRepository.deleteAll();
    }

    @Override
    public Integer getNewId() {
        return null;
    }

    @Override
    @Transactional
    public Flight getFlightById(Integer id) {
        return flightRepositoryDB.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFlight(Integer id) {
        flightRepositoryDB.deleteById(id);
    }

    @Override
    public List<Airport> getAirports(String search) {
        return airportRepository.findByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrAirportContainingIgnoreCase(search.trim(), search.trim(), search.trim());
    }

    @Override
    public List<Flight> searchFlights(SearchFlightsRequest request) {
        Airport fromAirport = airportRepository.findByAirportContainingIgnoreCase(request.getFrom().toUpperCase());
        Airport toAirport = airportRepository.findByAirportContainingIgnoreCase(request.getTo().toUpperCase());

        if (fromAirport == null || toAirport == null) {
            return List.of();
        }

        return flightRepositoryDB.findByFromAndToAndDepartureTime(fromAirport.getAirport(), toAirport.getAirport(), request.getDepartureDate());
    }

    private Airport findOrCreateAirport(Airport airport) {
        Airport existingAirport = airportRepository.findByAirportContainingIgnoreCase(airport.getAirport());
        return existingAirport != null ? existingAirport : airportRepository.save(airport);
    }
}
