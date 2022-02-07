package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import security.SecurityContextHolder;

import java.io.IOException;
import java.security.Principal;

public class AdminHomeFormController {

    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnManageUsers;
    public Button btnBackupRestore;
    public Button btnSignOut;
    public Label lblAdmin;

    public void initialize(){
        lblAdmin.setText("Welcome "+ SecurityContextHolder.getPrincipal().getName()+"!");
    }

    public void btnRecordAttendance_OnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/RecordAttendanceForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Attendance Marking System: Record Attendance");
        stage.setResizable(false);
        stage.initOwner(lblAdmin.getScene().getWindow());
        stage.show();
    }

    public void btnSignOut_OnAction(ActionEvent actionEvent) throws IOException {
        SecurityContextHolder.clear();
        Stage stage = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Attendance Marking System: Login");
        stage.setResizable(false);
        stage.show();
        ((Stage) lblAdmin.getScene().getWindow()).close();
    }

    public void btnViewReports_OnAction(ActionEvent actionEvent) {
    }

    public void btnUserProfile_OnAction(ActionEvent actionEvent) {
    }

    public void btnManageUsers_OnAction(ActionEvent actionEvent) {
    }

    public void btnBackupRestore_OnAction(ActionEvent actionEvent) {
    }
}
