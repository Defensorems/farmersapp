package com.farmersapp.data.repository;

import com.farmersapp.data.model.Location;
import java.util.ArrayList;
import java.util.List;

public class LocationRepository {

    // TODO: Implement data sources (local database, remote API, GPS)

    public LocationRepository() {
        // Временная реализация без реальных источников данных
    }

    public Location getCurrentLocation() {
        // Временная реализация с фиктивными данными
        return new Location(
                "Москва",
                55.7558,
                37.6173,
                "Россия",
                "Москва"
        );
    }

    public List<Location> searchLocations(String query) {
        // Временная реализация с фиктивными данными
        List<Location> locations = new ArrayList<>();

        if (query.toLowerCase().contains("москва")) {
            locations.add(new Location(
                    "Москва",
                    55.7558,
                    37.6173,
                    "Россия",
                    "Москва"
            ));
        }

        if (query.toLowerCase().contains("санкт") || query.toLowerCase().contains("петербург")) {
            locations.add(new Location(
                    "Санкт-Петербург",
                    59.9343,
                    30.3351,
                    "Россия",
                    "Санкт-Петербург"
            ));
        }

        return locations;
    }
}