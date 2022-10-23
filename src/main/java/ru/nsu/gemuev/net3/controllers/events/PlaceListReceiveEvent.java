package ru.nsu.gemuev.net3.controllers.events;

import ru.nsu.gemuev.net3.model.Place;

import java.util.List;

public record PlaceListReceiveEvent(List<Place> placeList) {
}
