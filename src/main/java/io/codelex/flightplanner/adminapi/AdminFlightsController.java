package io.codelex.flightplanner.adminapi;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin-api/flights")
public class AdminFlightsController {

    FlightsService flightsService;

    public AdminFlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping("{id}")
    public Flight getFlight(@PathVariable Integer id) {
        Flight flight = flightsService.getFlightById(id);
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return flight;
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@Valid @RequestBody AddFlightRequest request) {
        Flight flight = request.toDomain(flightsService.getNewId());
        if (flightsService.add(flight).isPresent()) {
            return flight;
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Integer id) {
        flightsService.deleteFlight(id);
    }

}
