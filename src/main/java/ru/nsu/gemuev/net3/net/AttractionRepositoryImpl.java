package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import ru.nsu.gemuev.net3.exceptions.NetException;
import ru.nsu.gemuev.net3.model.entities.AttractionPlace;
import ru.nsu.gemuev.net3.model.ports.AttractionRepository;
import ru.nsu.gemuev.net3.util.PropertyGetter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class AttractionRepositoryImpl implements AttractionRepository {

    @Override
    @SneakyThrows
    public List<AttractionPlace> getAllAttractions(double lat, double lng, double radius) {
        String apiKey = PropertyGetter.getPropertyOrThrow("opentripmap");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.opentripmap.com/0.1/ru/places/radius?radius=%s&lon=%s&lat=%s&apikey=%s"
                        .formatted(radius, lng, lat, apiKey)))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new NetException("Response status code: " + response.statusCode());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.readTree(response.body()).get("features").toString();

        return List.copyOf(objectMapper
                        .readValue(json, new TypeReference<List<AttractionRepositoryImpl.Helper>>() {
                        }))
                .stream()
                .map(h -> h.properties.toAttractionPlace())
                .collect(Collectors.toList());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("unused")
    @Data
    private static class Helper {
        Properties properties;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @SuppressWarnings("unused")
    @Data
    private static class Properties {
        String name;
        String kinds;
        String xid;

        public AttractionPlace toAttractionPlace() {
            return new AttractionPlace(name, kinds, xid);
        }
    }
}
