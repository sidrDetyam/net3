package ru.nsu.gemuev.net3.model.entities;

import lombok.Data;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Place {
    String country;
    String name;
    String city;
    String street;
    Coordinate coordinate;

    @Override
    public String toString(){
        return Stream.of(name, country, city, street)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }
}
