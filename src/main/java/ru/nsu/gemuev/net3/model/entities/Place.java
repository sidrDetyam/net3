package ru.nsu.gemuev.net3.model.entities;

import lombok.Data;

@Data
public class Place {
    String country;
    String name;
    String city;
    String street;
    Coordinate coordinate;
}
