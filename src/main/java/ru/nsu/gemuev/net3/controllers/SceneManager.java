package ru.nsu.gemuev.net3.controllers;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import ru.nsu.gemuev.net3.Main;
import ru.nsu.gemuev.net3.controllers.events.ShowMainViewEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowPlaceDescriptionViewEvent;
import ru.nsu.gemuev.net3.controllers.events.ShowPlaceListViewEvent;
import ru.nsu.gemuev.net3.exceptions.UIInitializeException;
import ru.nsu.gemuev.net3.util.DIContainer;

import java.io.IOException;

@Log4j2
@SuppressWarnings("unused")
public class SceneManager {
    @Setter
    private Stage stage;
    private final Scene mainScene;
    private final Scene placeListScene;
    private final Scene placeDescriptionScene;

    private static Parent loadFXML(@NonNull String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name));
        fxmlLoader.setControllerFactory(type -> DIContainer.getInjector().getInstance(type));
        return fxmlLoader.load();
    }

    public SceneManager() {
        try {
            placeListScene = new Scene(loadFXML("PlacesListView.fxml"), 600, 400);
            mainScene = new Scene(loadFXML("MainView.fxml"), 600, 400);
            placeDescriptionScene = new Scene(loadFXML("PlaceDescriptionView.fxml"), 600, 400);

        } catch (IOException e) {
            log.error("Ui init fail " + e.getMessage());
            throw new UIInitializeException("Ui init fail"  + e.getMessage());
        }
    }


    @Subscribe
    public void showPlaceListView(ShowPlaceListViewEvent e){
        stage.setScene(placeListScene);
    }

    @Subscribe
    public void showMainView(ShowMainViewEvent e){
        stage.setScene(mainScene);
    }

    @Subscribe
    public void showPlaceDescriptionView(ShowPlaceDescriptionViewEvent e){
        stage.setScene(placeDescriptionScene);
    }
}
