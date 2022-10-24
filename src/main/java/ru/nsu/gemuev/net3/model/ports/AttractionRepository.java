package ru.nsu.gemuev.net3.model.ports;

import ru.nsu.gemuev.net3.model.entities.Attraction;

import java.util.List;

public interface AttractionRepository {
    List<Attraction> getAllAttractions(double lat, double lng, double radius);
}
