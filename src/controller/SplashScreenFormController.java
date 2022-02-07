package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SplashScreenFormController {
    public Label lblStatus;
    private final SimpleObjectProperty<File> fileProperty = new SimpleObjectProperty<>();

    public void initialize(){
        establishDBConnection();
    }

    public void establishDBConnection(){
        lblStatus.setText("Establishing database connection...");
        new Thread(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance_marking_system","root","root");
                Platform.runLater(() -> {
                    loadLoginForm(connection);
                });
                sleep(2000);
                Platform.runLater(() -> lblStatus.setText("Setting up the UI..."));
                sleep(2000);

            } catch (SQLException  | ClassNotFoundException e) {
                /*Todo: first boot or restoring db*/
                if (e instanceof SQLException && ((SQLException) e).getSQLState().equals("42000")){
                    Platform.runLater(this::loadImportDBForm);
                }else{
                    shutdownApp(e);
                }
            }
        }).start();
    }

    private void loadImportDBForm() {
        try{
            Stage stage = new Stage();

            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/ImportDBForm.fxml"));
            AnchorPane root = fxmlLoader.load();
            ImportDBFormController controller = fxmlLoader.getController();
            controller.initFileProperty(fileProperty);

            Scene importDBScene = new Scene(root);
            stage.setScene(importDBScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(lblStatus.getScene().getWindow());
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setOnCloseRequest(event -> event.consume()); // Need to select an option before closing. Can't close the window without a selection.
            stage.showAndWait();

            if (fileProperty.getValue()==null){
                lblStatus.setText("Creating a new database...");
                new Thread(() -> {
                    try {
                        sleep(2000);
                        Platform.runLater(() -> lblStatus.setText("Loading database script..."));

                        InputStream is = this.getClass().getResourceAsStream("/assets/db-script.sql");
                        byte[] buffer = new byte[is.available()];
                        is.read(buffer);
                        String script = new String(buffer);
                        sleep(2000);

                        Platform.runLater(() -> lblStatus.setText("Executing database script..."));
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?allowMultiQueries=true","root","root");
                        Statement stm = connection.createStatement();
                        stm.execute(script);
                        connection.close();
                        sleep(2000);

                        Platform.runLater(() -> lblStatus.setText("Obtaining a new database connection..."));
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance_marking_system","root","root");
                        sleep(2000);

                        /* Storing the database connection as a singleton instance */
                        DBConnection.getInstance().init(connection);

                        /* Redirect to create admin form */
                        Platform.runLater(() -> {
                            lblStatus.setText("Setting up the UI...");
                            loadCreateAdminForm();
                        });


                    } catch (IOException | SQLException e) {
                        if (e instanceof SQLException)
                            dropDataBase();
                        shutdownApp(e);
                    }
                }).start();
            }else{
                /*Todo: Restore the backup*/
                System.out.println("Restoring the backup...");
            }
        } catch (IOException e) {
            shutdownApp(e);
        }
    }

    private void loadCreateAdminForm() {
        try {
            Stage stage = new Stage();
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("/view/CreateAdminForm.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Student Attendance System: Create Admin");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.sizeToScene();
            stage.show();

            /* Close the splash screen eventually */
            ((Stage) lblStatus.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLoginForm(Connection connection) {

        lblStatus.setText("Loading login form...");

        try{
            DBConnection.getInstance().init(connection);
            AnchorPane root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
            Scene loginScene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(loginScene);
            stage.setTitle("Student Attendance Marking System: Log In");
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
            ((Stage) lblStatus.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Log In not set yet");
        }
    }

    private void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            new Alert(Alert.AlertType.INFORMATION,"JVM can not be slept");
        }
    }

    private void shutdownApp(Throwable t){
        Platform.runLater(() -> {
            lblStatus.setText("Failed to initialize. Shutdown the app...");
            sleep(2000);
        });
        if (t!=null) t.printStackTrace();
        System.exit(1);
    }

    private void dropDataBase(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");
            Statement stm = connection.createStatement();
            stm.execute("DROP DATABASE IF EXISTS student_attendance_marking_system");
            connection.close();
        } catch (SQLException e) {
            shutdownApp(e);
        }

    }

}
