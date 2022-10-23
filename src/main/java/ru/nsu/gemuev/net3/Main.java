package ru.nsu.gemuev.net3;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.gemuev.net3.controllers.SceneSwitcher;
import ru.nsu.gemuev.net3.util.DIContainer;

public class Main extends Application {

    @Override
    public void start(Stage stage){
        stage.setTitle("Lab 3");

        SceneSwitcher sceneSwitcher = DIContainer.getInjector().getInstance(SceneSwitcher.class);
        sceneSwitcher.setStage(stage);
        sceneSwitcher.switchToMainScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}