package io.codelex.flightplanner.clientapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchFlightsRequest {
    @NotNull
    private final String from;
    @NotNull
    private final String to;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate departureDate;

    public SearchFlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.departureDate = LocalDate.parse(departureDate, formatter);
    }

    @AssertTrue
    public boolean isDifferentDepartureAndArrival() {
        return from != null && to != null && !from.equals(to);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }
}
