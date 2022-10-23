package ru.nsu.gemuev.net3.model;

import lombok.Data;

@Data
public class Place {
    String country;
    String name;
    Coordinate coordinate;
}
