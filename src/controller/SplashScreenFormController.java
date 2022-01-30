package controller;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SplashScreenFormController {
    public Label lblStatus;
    private File file;  //--------------------------Backup file to store

    public void initialize(){
        establishDBConnection();
    }

    public void establishDBConnection(){
        lblStatus.setText("Establishing database connection...");
//        sleepNow(3000);
        new Thread(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance_marking_system","root","root");
//                Platform.runLater(this::loadLoginForm);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                /*Todo: first boot or restoring db*/
                if (e.getSQLState().equals("42000"))
                Platform.runLater(this::loadImportDBForm);
                e.printStackTrace();
            }
        }).start();
    }

    private void loadImportDBForm() {
//        sleepNow(1000);
        lblStatus.setText("Database not found. Loading boot window...");
//        sleepNow(1000);
        try{
            SimpleObjectProperty<File> fileProperty = new SimpleObjectProperty<>();
            Stage stage = new Stage();
            AnchorPane root = FXMLLoader.load(getClass().getResource("/view/ImportDBForm.fxml"));
            Scene importDBScene = new Scene(root);
            stage.setScene(importDBScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(lblStatus.getScene().getWindow());
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error with database form");
        }
    }

    private void loadLoginForm() {
//        sleepNow(1000);
        lblStatus.setText("Loading login form...");
//        sleepNow(1000);
        try{
            AnchorPane root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
            Scene loginScene = new Scene(root);
            Stage stage = (Stage) lblStatus.getScene().getWindow();
            stage.setScene(loginScene);
            stage.sizeToScene();
            stage.centerOnScreen();
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Log In not set yet");
        }
    }

    public void sleepNow(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            new Alert(Alert.AlertType.INFORMATION,"JVM can not be slept");
        }
    }

}
