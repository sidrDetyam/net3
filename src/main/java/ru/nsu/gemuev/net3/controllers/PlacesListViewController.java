package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.nsu.gemuev.net3.controllers.events.ReturnToMainViewEvent;
import ru.nsu.gemuev.net3.controllers.events.PlaceListReceiveEvent;
import ru.nsu.gemuev.net3.model.Place;

import java.util.stream.Collectors;

public class PlacesListViewController {

    @FXML
    private ListView<String> placeList;
    @FXML
    private Button backToMainButton;

    private final EventBus eventBus;

    @Inject
    public PlacesListViewController(EventBus eventBus){
        this.eventBus = eventBus;
    }

    public void backToMainButtonPressed(){
        eventBus.post(new ReturnToMainViewEvent());
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void placeListReceive(PlaceListReceiveEvent e){
        ObservableList<String> items = FXCollections.observableArrayList(e.placeList()
                .stream()
                .map(Place::toString)
                .collect(Collectors.toList()));
        placeList.setItems(items);
    }
}
