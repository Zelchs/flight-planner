package io.codelex.flightplanner.adminapi;

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
    @NotBlank
    private final String departureTime;
    @Valid
    @NotBlank
    private final String arrivalTime;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    public AddFlightRequest(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @AssertTrue
    public boolean hasDifferentAirports() {
        try {
            return !from.equals(to);
        } catch (Exception e) {
            return false;
        }
    }

    @AssertTrue
    public boolean isArrivalAfterDeparture() {
        try {
            LocalDateTime departure = LocalDateTime.parse(this.departureTime, formatter);
            LocalDateTime arrival = LocalDateTime.parse(this.arrivalTime, formatter);
            return arrival.isAfter(departure);
        } catch (Exception e) {
            return false;
        }

    }

    public Flight toDomain(Integer id) {
        LocalDateTime departure = LocalDateTime.parse(departureTime, formatter);
        LocalDateTime arrival = LocalDateTime.parse(arrivalTime, formatter);
        return new Flight(id, from, to, carrier, departure, arrival);
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

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
}
