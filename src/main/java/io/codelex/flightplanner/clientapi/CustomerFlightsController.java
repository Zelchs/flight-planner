package io.codelex.flightplanner.clientapi;

import io.codelex.flightplanner.adminapi.Flight;
import io.codelex.flightplanner.adminapi.FlightsService;
import io.codelex.flightplanner.airports.Airport;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Airport>> getAirports(@RequestParam String search) {
        List<Airport> airports = flightsService.getAirports(search);
        if (airports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airports);
    }

    @GetMapping("/flights/{id}")
    public Flight getFlightById(@PathVariable Integer id) {
        if (flightsService.getFlightById(id) != null) {
            return flightsService.getFlightById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/flights/search")
    public ResponseEntity<Map<String, Object>> searchFlights(@Valid
                                                             @RequestBody SearchFlightsRequest request,
                                                             @RequestParam(defaultValue = "0") Integer page
    ) {
        System.out.println(request.getDepartureDate());
        List<Flight> foundFlights = flightsService.searchFlights(request);
        Integer totalItems = foundFlights.size();
        Map<String, Object> response = new HashMap<>();
        response.put("items", foundFlights);
        response.put("page", page);
        response.put("totalItems", totalItems);

        return ResponseEntity.ok(response);
    }


}
