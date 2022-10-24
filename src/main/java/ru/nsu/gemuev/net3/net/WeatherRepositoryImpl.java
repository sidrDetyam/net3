package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.nsu.gemuev.net3.model.entities.Weather;
import ru.nsu.gemuev.net3.model.ports.WeatherRepository;
import ru.nsu.gemuev.net3.util.PropertyGetter;

public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String url = "https://api.openweathermap.org/data/3.0/onecall?lat=%s&lon=%s&appid=%s";

    @Override
    @SneakyThrows
    public Weather getWeather(double lat, double lng) {
        String apiKey = PropertyGetter.getPropertyOrThrow("openweather");

        String body = RequestSender.getRequestBody(url.formatted(lat, lng, apiKey));
        ObjectMapper objectMapper = new ObjectMapper();
        var tree = objectMapper.readTree(body).get("current");
        Weather weather = new Weather();
        weather.setTemperature(Double.parseDouble(tree.get("temp").toString()));
        weather.setPressure(Double.parseDouble(tree.get("pressure").toString()));
        return weather;
    }
}
