package ru.nsu.gemuev.net3.model.ports;

import ru.nsu.gemuev.net3.model.entities.Place;

import java.util.List;

public interface PlaceRepository {
    List<Place> getAllPlaces(String placeName);
}
