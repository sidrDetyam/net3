package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@Data
class Helper {
    private Properties properties;
}
