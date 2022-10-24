package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.nsu.gemuev.net3.model.entities.Coordinate;
import ru.nsu.gemuev.net3.model.entities.Place;
import ru.nsu.gemuev.net3.model.ports.PlaceRepository;
import ru.nsu.gemuev.net3.util.PropertyGetter;

import java.util.List;

public class PlaceRepositoryImpl implements PlaceRepository {

    private static final String placesUrl = "https://graphhopper.com/api/1/geocode?q=%s&key=%s&locale=ru&limit=25";

    @Override
    @SneakyThrows
    public List<Place> getAllPlaces(@NonNull String placeName) {
        String apiKey = PropertyGetter.getPropertyOrThrow("graphhopper");

        String responseBody = RequestSender.getRequestBody(placesUrl.formatted(placeName, apiKey));
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.readTree(responseBody).get("hits").toString();
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
