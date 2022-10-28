package ru.nsu.gemuev.net3.net;

import lombok.NonNull;
import lombok.SneakyThrows;
import ru.nsu.gemuev.net3.exceptions.NetException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class RequestSender {

    private static final Duration TIME_OUT = Duration.ofMillis(10000);

    private RequestSender(){}

    @SneakyThrows
    public static String getResponse(@NonNull String req){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(req))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                .connectTimeout(TIME_OUT)
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new NetException("Response status code: " + response.statusCode());
        }

        return response.body();
    }
}
