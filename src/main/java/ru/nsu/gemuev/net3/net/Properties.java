package ru.nsu.gemuev.net3.net;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import ru.nsu.gemuev.net3.model.entities.Attraction;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
@Data
class Properties {
    private String name;
    private String kinds;
    private String xid;
    @JsonIgnoreProperties
    private String description;

    public Attraction toAttraction() {
        return new Attraction(name, kinds, description);
    }
}