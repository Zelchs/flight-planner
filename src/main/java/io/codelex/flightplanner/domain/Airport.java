package io.codelex.flightplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private String country;

    @NotEmpty
    @NotNull
    @Column(nullable = false)
    private String city;

    @NotEmpty
    @NotNull
    @Column(nullable = false, unique = true)
    private String airport;

    public Airport() {
    }

    private String capitalizeWords(String input) {
        String[] words = input.split("\\s+");
        StringBuilder capitalizedWords = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                if (!capitalizedWords.isEmpty()) capitalizedWords.append(" ");
                capitalizedWords.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase());
            }
        }
        return capitalizedWords.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = capitalizeWords(country.trim());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = capitalizeWords(city.trim());
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport.toUpperCase().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return Objects.equals(country, airport1.country) && Objects.equals(city, airport1.city) && Objects.equals(airport, airport1.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}
