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
import ru.nsu.gemuev.net3.controllers.events.PlaceSelectedEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowMainViewEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowPlaceListViewEvent;
import ru.nsu.gemuev.net3.model.entities.AttractionPlace;
import ru.nsu.gemuev.net3.model.entities.Place;
import ru.nsu.gemuev.net3.model.usecases.AttractionsNearPlace;
import ru.nsu.gemuev.net3.model.usecases.WeatherNearPlace;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class AttractionsListController {

    @FXML
    private Label weatherLabel;
    @FXML
    private ListView<AttractionPlace> attractionList;
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

    public void showMainView() {
        eventBus.post(new ShowMainViewEvent());
    }

    public void showListView() {
        eventBus.post(new ShowPlaceListViewEvent());
    }

    private void setItemsList(List<AttractionPlace> attractionPlaces){
        ObservableList<AttractionPlace> items = FXCollections.observableArrayList(attractionPlaces);
        attractionList.setItems(items);
    }

    private void setWeatherLabel(String weatherInfo){
        weatherLabel.setText(weatherInfo);
    }

    @Subscribe
    public void placeSelectedEvent(PlaceSelectedEvent e) {
        Place place = e.getSelectedPlace();
        progressIndicator.setVisible(true);

        var cf1 = CompletableFuture.supplyAsync(() -> attractionsNearPlace
                        .getAttractionPlaces(place.getCoordinate().getLat(), place.getCoordinate().getLng(), 1000))
                .thenAccept(attractionPlaces -> Platform.runLater(() -> setItemsList(attractionPlaces)));

        var cf2 = CompletableFuture.supplyAsync(() -> weatherNearPlace
                .getWeather(place.getCoordinate().getLat(), place.getCoordinate().getLng()))
                        .handle((weather, exception) -> {
                            if(exception!=null){
                                System.out.println(exception);
                            }
                            return 0;
                        });

        CompletableFuture.allOf(cf1, cf2).thenAccept(__ ->
                Platform.runLater(() -> progressIndicator.setVisible(false)));
    }
}
