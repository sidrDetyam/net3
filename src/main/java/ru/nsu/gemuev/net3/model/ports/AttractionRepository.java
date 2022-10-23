package ru.nsu.gemuev.net3.model.ports;

import ru.nsu.gemuev.net3.model.entities.AttractionPlace;

import java.util.List;

public interface AttractionRepository {
    List<AttractionPlace> getAllAttractions(double lat, double lng, double radius);
}
