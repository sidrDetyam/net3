package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import lombok.NonNull;
import ru.nsu.gemuev.net3.controllers.events.ShowMainViewEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowPlaceListViewEvent;

@SuppressWarnings("unused")
public class PlaceDescriptionController {

    private final EventBus eventBus;

    @Inject
    public PlaceDescriptionController(@NonNull EventBus eventBus){
        this.eventBus = eventBus;
    }

    public void showMainView(){
        eventBus.post(new ShowMainViewEvent());
    }

    public void showListView(){
        eventBus.post(new ShowPlaceListViewEvent());
    }
}
