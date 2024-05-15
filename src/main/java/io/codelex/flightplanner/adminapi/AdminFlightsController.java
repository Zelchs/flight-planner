package io.codelex.flightplanner.adminapi;

import jakarta.servlet.http.HttpServletResponse;
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
        if (flightsService.getFlightById(id) != null) {
            return flightsService.getFlightById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public Flight addFlight(HttpServletResponse response, @Valid @RequestBody AddFlightRequest request) {
        Flight flight = request.toDomain(flightsService.getNewId());
        if (flightsService.add(flight).isPresent()) {
            response.setStatus(HttpStatus.CREATED.value());
            return flight;
        }
        response.setStatus(HttpStatus.CONFLICT.value());
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable Integer id) {
        flightsService.deleteFlight(id);
    }

}
