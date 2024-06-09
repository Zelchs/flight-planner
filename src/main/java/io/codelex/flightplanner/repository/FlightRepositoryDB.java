package io.codelex.flightplanner.repository;

import io.codelex.flightplanner.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepositoryDB extends JpaRepository<Flight, Integer> {

    @Query("SELECT f FROM Flight f WHERE f.from.airport = :fromCode AND f.to.airport = :toCode AND CAST(f.departureTime AS date) = :departureTime")
    List<Flight> findByFromAndToAndDepartureTime(
            @Param("fromCode") String fromCode,
            @Param("toCode") String toCode,
            @Param("departureTime") LocalDate departureTime
    );

    @Query("SELECT f FROM Flight f WHERE f.from.airport = :fromCode AND f.to.airport = :toCode AND f.carrier = :carrier AND f.departureTime = :departureTime AND f.arrivalTime = :arrivalTime")
    List<Flight> findByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(
            @Param("fromCode") String fromCode,
            @Param("toCode") String toCode,
            @Param("carrier") String carrier,
            @Param("departureTime") LocalDateTime departureTime,
            @Param("arrivalTime") LocalDateTime arrivalTime
    );

}
