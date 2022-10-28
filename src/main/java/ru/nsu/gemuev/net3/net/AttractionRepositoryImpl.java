package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import ru.nsu.gemuev.net3.model.entities.Attraction;
import ru.nsu.gemuev.net3.model.ports.AttractionRepository;
import ru.nsu.gemuev.net3.util.PropertyGetter;

import java.util.List;
import java.util.stream.Collectors;

public class AttractionRepositoryImpl implements AttractionRepository {

    private static final String placesInRadiusUrl = "https://api.opentripmap.com/0.1/ru/places/radius?radius=%s&lon=%s&lat=%s&apikey=%s";
    private static final String placeDescriptionUrl = "https://api.opentripmap.com/0.1/ru/places/xid/%s?apikey=%s";

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String getDescriptionByXid(@NonNull String xid) {
        try {
            String apiKey = PropertyGetter.getPropertyOrThrow("opentripmap");
            return objectMapper.readTree(RequestSender.getResponse(placeDescriptionUrl.formatted(xid, apiKey)))
                    .get("kinds").toString();
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    @Override
    @SneakyThrows
    public List<Attraction> getAllAttractions(double lat, double lng, double radius) {
        String apiKey = PropertyGetter.getPropertyOrThrow("opentripmap");
        String responseBody = RequestSender
                .getResponse(placesInRadiusUrl.formatted(radius, lng, lat, apiKey));

        String json = objectMapper.readTree(responseBody).get("features").toString();

        return objectMapper.readValue(json, new TypeReference<List<Helper>>() {
                })
                .stream()
                .map(Helper::getProperties)
                .map(p -> {
                    p.setDescription(getDescriptionByXid(p.getXid()));
                    return p.toAttraction();
                })
                .collect(Collectors.toList());
    }
}
