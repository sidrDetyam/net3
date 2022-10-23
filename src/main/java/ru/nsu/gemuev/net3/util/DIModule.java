package ru.nsu.gemuev.net3.util;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.nsu.gemuev.net3.controllers.MainViewController;
import ru.nsu.gemuev.net3.controllers.PlacesListViewController;
import ru.nsu.gemuev.net3.controllers.SceneSwitcher;
import ru.nsu.gemuev.net3.model.PlaceEntered;
import ru.nsu.gemuev.net3.net.PlaceRepositoryImpl;

public class DIModule extends AbstractModule {

    @Provides
    @Singleton
    EventBus getEventBus(){
        return new EventBus();
    }

    @Provides
    PlaceEntered getPlaceEntered(){
        return new PlaceEntered(new PlaceRepositoryImpl());
    }

    @Provides
    SceneSwitcher getSceneSwitcher(EventBus eventBus){
        SceneSwitcher sceneSwitcher = new SceneSwitcher();
        eventBus.register(sceneSwitcher);
        return sceneSwitcher;
    }

    @Provides
    MainViewController getMainViewController(EventBus eventBus, PlaceEntered placeEntered){
        MainViewController controller = new MainViewController(eventBus, placeEntered);
        eventBus.register(controller);
        return controller;
    }

    @Provides
    PlacesListViewController getPlacesListViewController(EventBus eventBus){
        PlacesListViewController controller = new PlacesListViewController(eventBus);
        eventBus.register(controller);
        return controller;
    }
}
