package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.net3.controllers.events.PlaceSelectedEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowPlaceListViewEvent;
import ru.nsu.gemuev.net3.model.entities.Attraction;
import ru.nsu.gemuev.net3.model.entities.Place;
import ru.nsu.gemuev.net3.model.usecases.AttractionsNearPlace;
import ru.nsu.gemuev.net3.model.usecases.WeatherNearPlace;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
@Log4j2
public class AttractionsListController {

    @FXML
    private Label weatherLabel;
    @FXML
    private ListView<Attraction> attractionList;
    @FXML
    private ProgressIndicator progressIndicator;

    private final AttractionsNearPlace attractionsNearPlace;

    private final WeatherNearPlace weatherNearPlace;

    private final EventBus eventBus;

    @Inject
    public AttractionsListController(EventBus eventBus, AttractionsNearPlace attractionsNearPlace, WeatherNearPlace weatherNearPlace) {
        this.eventBus = eventBus;
        this.attractionsNearPlace = attractionsNearPlace;
        this.weatherNearPlace = weatherNearPlace;
    }

    public void showListView() {
        eventBus.post(new ShowPlaceListViewEvent());
    }

    private void setItemsList(List<Attraction> attractionPlaces){
        Platform.runLater(() -> {
            ObservableList<Attraction> items = FXCollections.observableArrayList(attractionPlaces);
            attractionList.setItems(items);
        });
    }

    private void setWeatherLabel(String weatherInfo){
        Platform.runLater(() -> weatherLabel.setText(weatherInfo));
    }

    @Subscribe
    public void placeSelectedEvent(PlaceSelectedEvent e) {
        Place place = e.getSelectedPlace();
        progressIndicator.setVisible(true);

        var attractionsRequest = CompletableFuture.supplyAsync(() -> attractionsNearPlace
                        .getAttractionPlaces(place.getCoordinate().getLat(), place.getCoordinate().getLng(), 250))
                .thenAccept(this::setItemsList);

        var weatherRequest = CompletableFuture.supplyAsync(() -> weatherNearPlace
                .getWeather(place.getCoordinate().getLat(), place.getCoordinate().getLng()))
                .thenAccept(weather -> setWeatherLabel(weather.toString()));

        CompletableFuture.allOf(attractionsRequest, weatherRequest).handle((__, ex) -> {
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
                if(ex != null){
                    ErrorAlert.showErrorAlert(ex.getMessage());
                    log.error(ex);
                }
            });
            return null;
        });
    }
}
