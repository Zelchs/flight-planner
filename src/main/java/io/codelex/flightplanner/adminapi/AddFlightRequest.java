package io.codelex.flightplanner.adminapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.airports.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddFlightRequest {

    @Valid
    @NotNull
    private final Airport from;
    @Valid
    @NotNull
    private final Airport to;
    @Valid
    @NotBlank
    private final String carrier;
    @Valid
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime departureTime;
    @Valid
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private final LocalDateTime arrivalTime;


    public AddFlightRequest(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.departureTime = LocalDateTime.parse(departureTime, formatter);
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);
    }

    @AssertTrue
    public boolean hasDifferentAirports() {
        return from != null && to != null && !from.equals(to);
    }

    @AssertTrue
    public boolean isArrivalAfterDeparture() {
        return departureTime != null && arrivalTime != null && departureTime.isBefore(arrivalTime);
    }

    public Flight toDomain(Integer id) {
        return new Flight(id, from, to, carrier, departureTime, arrivalTime);
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public String getCarrier() {
        return carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
