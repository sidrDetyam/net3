package ru.nsu.gemuev.net3.model.ports;

import ru.nsu.gemuev.net3.model.entities.Weather;

public interface WeatherRepository {
    Weather getWeather(double lat, double lng);
}
