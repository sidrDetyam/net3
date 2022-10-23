package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.nsu.gemuev.net3.exceptions.NetException;
import ru.nsu.gemuev.net3.model.Coordinate;
import ru.nsu.gemuev.net3.model.Place;
import ru.nsu.gemuev.net3.model.PlaceRepository;
import ru.nsu.gemuev.net3.util.PropertyGetter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PlaceRepositoryImpl implements PlaceRepository {

    @Override
    @SneakyThrows
    public List<Place> getAllPlaces(@NonNull String placeName) {
        String apiKey = PropertyGetter.getPropertyOrThrow("graphhopper");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://graphhopper.com/api/1/geocode?q=%s&key=%s&locale=ru"
                        .formatted(placeName, apiKey)))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new NetException("Response status code: " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.readTree(response.body()).get("hits").toString();
        return List.copyOf(objectMapper.readValue(json, new TypeReference<List<PlaceJackson>>() {}));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("unused")
    private static class PlaceJackson extends Place {
        public void setPoint(Coordinate coord) {
            setCoordinate(coord);
        }
    }
}
