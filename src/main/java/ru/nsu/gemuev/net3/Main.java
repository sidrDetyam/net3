package ru.nsu.gemuev.net3;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.gemuev.net3.controllers.SceneManager;
import ru.nsu.gemuev.net3.controllers.events.ShowMainViewEvent;
import ru.nsu.gemuev.net3.util.DIContainer;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("Lab 3");

        SceneManager sceneSwitcher = DIContainer.getInjector().getInstance(SceneManager.class);
        sceneSwitcher.setStage(stage);
        sceneSwitcher.showMainView(new ShowMainViewEvent());
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}