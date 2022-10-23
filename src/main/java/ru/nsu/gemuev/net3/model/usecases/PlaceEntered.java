package ru.nsu.gemuev.net3.model.usecases;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.nsu.gemuev.net3.model.ports.PlaceRepository;
import ru.nsu.gemuev.net3.model.entities.Place;

import java.util.List;

@RequiredArgsConstructor
public class PlaceEntered {
    @NonNull
    private PlaceRepository placeRepository;

    public List<Place> listOfPlaces(@NonNull String placeName){
        return placeRepository.getAllPlaces(placeName);
    }
}
