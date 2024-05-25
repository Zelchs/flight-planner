package io.codelex.flightplanner.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record Airport(@NotEmpty @NotNull String country, @NotEmpty @NotNull String city,
                      @NotEmpty @NotNull String airport) {
    public Airport(String country, String city, String airport) {
        this.country = capitalizeWords(country.trim());
        this.city = capitalizeWords(city.trim());
        this.airport = airport.toUpperCase().trim();
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

}
