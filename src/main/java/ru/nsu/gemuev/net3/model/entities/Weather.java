package ru.nsu.gemuev.net3.model.entities;

import lombok.Data;

@Data
public class Weather {
    String type;
    Double windSpeed;
    Double temperature;
}
