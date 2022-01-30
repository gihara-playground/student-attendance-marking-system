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
import java.io.InputStream;
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
//        sleep(3000);
        new Thread(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance_marking_system","root","root");
//                Platform.runLater(this::loadLoginForm);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                /*Todo: first boot or restoring db*/
                if (e.getSQLState().equals("42000")){
                    Platform.runLater(this::loadImportDBForm);
                }
                else{
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void loadImportDBForm() {
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
            file = fileProperty.getValue();

            if (file==null){
                lblStatus.setText("Creating a new database...");
                new Thread(() -> {
                    try {
                        sleep(1000);
                        Platform.runLater(() -> lblStatus.setText("Loading database script..."));

                        InputStream is = this.getClass().getResourceAsStream("/assets/db-script.sql");
                        byte[] buffer = new byte[is.available()];
                        is.read(buffer);
                        String script = new String(buffer);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }else{
                /*Todo: Restore the backup*/
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Error with database form");
        }
    }

/*    private void loadLoginForm() {
//        sleep(1000);
        lblStatus.setText("Loading login form...");
//        sleep(1000);
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
    }*/

    public void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            new Alert(Alert.AlertType.INFORMATION,"JVM can not be slept");
        }
    }

}
