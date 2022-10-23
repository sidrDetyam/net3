package ru.nsu.gemuev.net3.model;

import java.util.List;

public interface PlaceRepository {
    List<Place> getAllPlaces(String placeName);
}
