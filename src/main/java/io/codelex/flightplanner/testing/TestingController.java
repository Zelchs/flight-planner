package io.codelex.flightplanner.testing;

import io.codelex.flightplanner.adminapi.FlightsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testing-api")
public class TestingController {

    private final FlightsService flightsService;

    public TestingController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @PostMapping("/clear")
    public void clear() {
        flightsService.clear();
    }
}
