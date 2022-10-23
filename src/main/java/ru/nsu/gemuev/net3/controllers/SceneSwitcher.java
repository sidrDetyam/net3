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
import ru.nsu.gemuev.net3.controllers.events.ReturnToMainViewEvent;
import ru.nsu.gemuev.net3.controllers.events.PlaceListReceiveEvent;
import ru.nsu.gemuev.net3.exceptions.UIInitializeException;
import ru.nsu.gemuev.net3.util.DIContainer;

import java.io.IOException;

@Log4j2
public class SceneSwitcher {
    @Setter
    private Stage stage;
    private final Scene mainScene;
    private final Scene placeListScene;

    private static Parent loadFXML(@NonNull String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(name));
        fxmlLoader.setControllerFactory(type -> DIContainer.getInjector().getInstance(type));
        return fxmlLoader.load();
    }

    public SceneSwitcher() {
        try {
            placeListScene = new Scene(loadFXML("PlacesListView.fxml"), 600, 400);
            mainScene = new Scene(loadFXML("MainView.fxml"), 600, 400);

        } catch (IOException e) {
            log.error("Ui init fail " + e.getMessage());
            throw new UIInitializeException("Ui init fail"  + e.getMessage());
        }
    }

    public void switchToMainScene(){
        stage.setScene(mainScene);
    }

    public void switchToPlaceListScene(){
        stage.setScene(placeListScene);
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void placeListReceiveEvent(PlaceListReceiveEvent e){
        switchToPlaceListScene();
    }

    @Subscribe
    @SuppressWarnings("unused")
    public void backToMainView(ReturnToMainViewEvent e){
        switchToMainScene();
    }
}
