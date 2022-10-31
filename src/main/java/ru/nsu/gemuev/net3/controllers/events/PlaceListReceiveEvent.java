package ru.nsu.gemuev.net3.controllers.events;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.nsu.gemuev.net3.model.entities.Place;

import java.util.List;

@RequiredArgsConstructor
public class PlaceListReceiveEvent extends ShowPlaceListViewEvent{
    @NonNull
    @Getter
    private final List<Place> placeList;
}
