package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public record Flight(
        Integer id, Airport from, Airport to, String carrier,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime departureTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime arrivalTime) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(to, flight.to) && Objects.equals(from, flight.from) && Objects.equals(carrier, flight.carrier) && Objects.equals(arrivalTime, flight.arrivalTime) && Objects.equals(departureTime, flight.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, carrier, departureTime, arrivalTime);
    }
}


