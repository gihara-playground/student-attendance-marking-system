package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SplashScreenFormController {
    public Label lblStatus;

    public void initialize(){
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    try{
                        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/UserHomeForm.fxml"));
                        Scene loginScene = new Scene(root);
                        Stage stage = (Stage) lblStatus.getScene().getWindow();
                        stage.setScene(loginScene);
                        stage.sizeToScene();
                        stage.centerOnScreen();
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
