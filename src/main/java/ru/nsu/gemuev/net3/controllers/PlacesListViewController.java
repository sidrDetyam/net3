package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import ru.nsu.gemuev.net3.controllers.events.PlaceListReceiveEvent;
import ru.nsu.gemuev.net3.controllers.events.PlaceSelectedEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowMainViewEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowPlaceListViewEvent;
import ru.nsu.gemuev.net3.model.entities.Place;

import java.util.List;

@SuppressWarnings("unused")
public class PlacesListViewController {

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

    @Subscribe
    public void showPlaceListEvent(ShowPlaceListViewEvent e){
        MultipleSelectionModel<Place> selectionModel = placeList.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<>() {
            public void changed(ObservableValue<? extends Place> __, Place ___, Place selectedPlace) {
                if (selectedPlace != null) {
                    selectionModel.selectedItemProperty().removeListener(this);
                    eventBus.post(new PlaceSelectedEvent(selectedPlace));
                }
            }
        });
    }
}
