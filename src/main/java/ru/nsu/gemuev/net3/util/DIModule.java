package ru.nsu.gemuev.net3.util;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import ru.nsu.gemuev.net3.controllers.MainViewController;
import ru.nsu.gemuev.net3.controllers.AttractionsListController;
import ru.nsu.gemuev.net3.controllers.PlacesListViewController;
import ru.nsu.gemuev.net3.controllers.SceneManager;
import ru.nsu.gemuev.net3.model.usecases.AttractionsNearPlace;
import ru.nsu.gemuev.net3.model.usecases.PlaceNameEntered;
import ru.nsu.gemuev.net3.model.usecases.WeatherNearPlace;
import ru.nsu.gemuev.net3.net.AttractionRepositoryImpl;
import ru.nsu.gemuev.net3.net.PlaceRepositoryImpl;
import ru.nsu.gemuev.net3.net.WeatherRepositoryImpl;

public class DIModule extends AbstractModule {

    @Provides
    @Singleton
    EventBus getEventBus() {
        return new EventBus();
    }

    @Provides
    PlaceNameEntered getPlaceEntered() {
        return new PlaceNameEntered(new PlaceRepositoryImpl());
    }

    @Provides
    AttractionsNearPlace getAttractionsNearPlace() {
        return new AttractionsNearPlace(new AttractionRepositoryImpl());
    }

    @Provides
    WeatherNearPlace getWheWeatherNearPlace() {
        return new WeatherNearPlace(new WeatherRepositoryImpl());
    }

    @Provides
    SceneManager getSceneSwitcher(EventBus eventBus) {
        SceneManager sceneSwitcher = new SceneManager();
        eventBus.register(sceneSwitcher);
        return sceneSwitcher;
    }

    @Provides
    MainViewController getMainViewController(EventBus eventBus, PlaceNameEntered placeEntered) {
        MainViewController controller = new MainViewController(eventBus, placeEntered);
        eventBus.register(controller);
        return controller;
    }

    @Provides
    PlacesListViewController getPlacesListViewController(EventBus eventBus) {
        PlacesListViewController controller = new PlacesListViewController(eventBus);
        eventBus.register(controller);
        return controller;
    }

    @Provides
    AttractionsListController getPlaceDescriptionController(EventBus eventBus,
                                                            AttractionsNearPlace attractionsNearPlace,
                                                            WeatherNearPlace weatherNearPlace) {
        AttractionsListController controller = new AttractionsListController(eventBus,
                attractionsNearPlace, weatherNearPlace);
        eventBus.register(controller);
        return controller;
    }
}
