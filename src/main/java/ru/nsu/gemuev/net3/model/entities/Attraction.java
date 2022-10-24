package ru.nsu.gemuev.net3.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attraction {
    String name;
    String type;
    String description;

    @Override
    public String toString(){
        return Stream.of(name, description)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }
}
