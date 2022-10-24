package ru.nsu.gemuev.net3.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.NonNull;

public class ErrorAlert {

    private ErrorAlert(){}

    public static void showErrorAlert(@NonNull String errorMessage){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something goes wrong...");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        });
    }
}
