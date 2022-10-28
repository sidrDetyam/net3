package ru.nsu.gemuev.net3.model.entities;

import lombok.Data;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class Place {
    private String country;
    private String name;
    private String city;
    private String street;
    private Coordinate coordinate;

    @Override
    public String toString(){
        return Stream.of(name, country, city, street)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }
}
