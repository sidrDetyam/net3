package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.net3.model.entities.Weather;
import ru.nsu.gemuev.net3.model.ports.WeatherRepository;
import ru.nsu.gemuev.net3.util.PropertyGetter;

@Log4j2
public class WeatherRepositoryImpl implements WeatherRepository {

    private static final String url = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";

    @Override
    @SneakyThrows
    public Weather getWeather(double lat, double lng) {
        String apiKey = PropertyGetter.getPropertyOrThrow("openweather");

        String requestUrl = url.formatted(lat, lng, apiKey);
        try {
            String body = RequestSender.getResponse(requestUrl);
            ObjectMapper objectMapper = new ObjectMapper();
            var tree = objectMapper.readTree(body).get("current");
            Weather weather = new Weather();
            weather.setTemperature(Double.parseDouble(tree.get("temp").toString()));
            weather.setPressure(Double.parseDouble(tree.get("pressure").toString()));
            return weather;
        }
        catch(Throwable e){
            log.error(requestUrl);
            throw e;
        }
    }
}
