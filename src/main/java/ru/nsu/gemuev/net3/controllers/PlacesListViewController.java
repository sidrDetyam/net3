package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import ru.nsu.gemuev.net3.controllers.events.PlaceListReceiveEvent;
import ru.nsu.gemuev.net3.controllers.events.PlaceSelectedEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowMainViewEvent;
import ru.nsu.gemuev.net3.model.entities.Place;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("unused")
public class PlacesListViewController implements Initializable {

    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private ListView<Place> placeList;
    @FXML
    private Button backToMainButton;

    private final EventBus eventBus;

    @Inject
    public PlacesListViewController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void showMainView() {
        eventBus.post(new ShowMainViewEvent());
    }

    @Subscribe
    public void placeListReceive(PlaceListReceiveEvent e) {
        ObservableList<Place> items = FXCollections.observableArrayList(List.copyOf(e.getPlaceList()));
        placeList.setItems(items);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MultipleSelectionModel<Place> selectionModel = placeList.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        selectionModel.selectedItemProperty().addListener((__, ___, selectedPlace) -> {
            if (selectedPlace != null) {
                eventBus.post(new PlaceSelectedEvent(selectedPlace));
            }
        });
    }
}
