package ru.nsu.gemuev.net3.model.entities;

import lombok.Data;

@Data
public class Weather {
    private double pressure;
    private double temperature;

    @Override
    public String toString(){
        return "pressure: %s, temperature: %s".formatted(pressure, temperature);
    }
}
