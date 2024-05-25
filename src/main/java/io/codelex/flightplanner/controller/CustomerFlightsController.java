package io.codelex.flightplanner.controller;

import io.codelex.flightplanner.domain.Flight;
import io.codelex.flightplanner.service.FlightsService;
import io.codelex.flightplanner.domain.Airport;
import io.codelex.flightplanner.request.SearchFlightsRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CustomerFlightsController {

    FlightsService flightsService;

    public CustomerFlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping("/airports")
    public List<Airport> getAirports(@RequestParam String search) {
        return flightsService.getAirports(search);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlightById(@PathVariable Integer id) {
        if (flightsService.getFlightById(id) != null) {
            return flightsService.getFlightById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/flights/search")
    public Map<String, Object> searchFlights
            (
                    @Valid
                    @RequestBody SearchFlightsRequest request,
                    @RequestParam(defaultValue = "0") Integer page
            ) {
        List<Flight> foundFlights = flightsService.searchFlights(request);
        Integer totalItems = foundFlights.size();
        Map<String, Object> response = new HashMap<>();
        response.put("items", foundFlights);
        response.put("page", page);
        response.put("totalItems", totalItems);

        return response;
    }


}
