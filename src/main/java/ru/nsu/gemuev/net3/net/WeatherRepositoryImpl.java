package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import ru.nsu.gemuev.net3.exceptions.NetException;
import ru.nsu.gemuev.net3.model.entities.Weather;
import ru.nsu.gemuev.net3.model.ports.WeatherRepository;
import ru.nsu.gemuev.net3.util.PropertyGetter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WeatherRepositoryImpl implements WeatherRepository {

    @Override
    @SneakyThrows
    public Weather getWeather(double lat, double lng) {
        String apiKey = PropertyGetter.getPropertyOrThrow("openweather");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s"
                        .formatted(lat, lng, apiKey)))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new NetException("Response status code: " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(response.body());
        return null;
    }
}
