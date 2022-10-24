package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.net3.controllers.events.PlaceListReceiveEvent;
import ru.nsu.gemuev.net3.model.usecases.PlacesByName;

import java.util.concurrent.CompletableFuture;

@Log4j2
public class MainViewController {
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private TextField placeNameField;
    private final PlacesByName placeEntered;
    private final EventBus eventBus;

    @Inject
    public MainViewController(EventBus eventBus, PlacesByName placeEntered) {
        this.eventBus = eventBus;
        this.placeEntered = placeEntered;
    }

    @FXML
    public void onPlaceNameInput() {
        progressIndicator.setVisible(true);
        CompletableFuture
                .supplyAsync(() -> placeEntered.listOfPlaces(placeNameField.getText()))
                .handle((placeList, exception) -> {
                    Platform.runLater(() -> {
                        progressIndicator.setVisible(false);
                        if (placeList != null) {
                            eventBus.post(new PlaceListReceiveEvent(placeList));
                        }
                        else{
                            log.error(exception.getMessage());
                            ErrorAlert.showErrorAlert(exception.getMessage());
                        }
                    });
                    return null;
                });
    }
}