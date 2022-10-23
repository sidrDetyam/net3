package ru.nsu.gemuev.net3.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PlaceEntered {
    @NonNull
    private PlaceRepository placeRepository;

    public List<Place> listOfPlaces(@NonNull String placeName){
        return placeRepository.getAllPlaces(placeName);
    }
}
