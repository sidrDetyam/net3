package ru.nsu.gemuev.net3.model.usecases;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.nsu.gemuev.net3.model.entities.Attraction;
import ru.nsu.gemuev.net3.model.ports.AttractionRepository;

import java.util.List;

@RequiredArgsConstructor
public class AttractionsNearPlace {
    @NonNull
    private AttractionRepository attractionRepository;

    public List<Attraction> getAttractionPlaces(double lat, double lng, double radius){
        return attractionRepository.getAllAttractions(lat, lng, radius);
    }
}
