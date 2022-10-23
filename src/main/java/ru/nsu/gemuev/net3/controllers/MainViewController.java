package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import ru.nsu.gemuev.net3.controllers.events.PlaceListReceiveEvent;
import ru.nsu.gemuev.net3.model.usecases.PlaceEntered;

import java.util.concurrent.CompletableFuture;

public class MainViewController {
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextField placeNameField;
    private final PlaceEntered placeEntered;
    private final EventBus eventBus;

    @Inject
    public MainViewController(EventBus eventBus, PlaceEntered placeEntered) {
        this.eventBus = eventBus;
        this.placeEntered = placeEntered;
    }

    @FXML
    public void onPlaceNameInput() {
        progressIndicator.setVisible(true);
        CompletableFuture
                .supplyAsync(() -> placeEntered.listOfPlaces(placeNameField.getText()))
                .thenAccept(placeList -> Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                    eventBus.post(new PlaceListReceiveEvent(placeList));
        }));
    }
}