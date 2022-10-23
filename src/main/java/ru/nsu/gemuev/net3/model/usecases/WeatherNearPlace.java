package ru.nsu.gemuev.net3.model.usecases;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.nsu.gemuev.net3.model.entities.Weather;
import ru.nsu.gemuev.net3.model.ports.WeatherRepository;

@RequiredArgsConstructor
public class WeatherNearPlace {
    @NonNull
    private WeatherRepository weatherRepository;

    public Weather getWeather(double lat, double lng){
        return weatherRepository.getWeather(lat, lng);
    }
}
