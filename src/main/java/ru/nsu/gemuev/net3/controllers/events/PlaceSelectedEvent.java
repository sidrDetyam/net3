package ru.nsu.gemuev.net3.controllers.events;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.nsu.gemuev.net3.model.entities.Place;

@RequiredArgsConstructor
public class PlaceSelectedEvent extends ShowPlaceDescriptionViewEvent{
    @NonNull
    @Getter
    private final Place selectedPlace;
}
