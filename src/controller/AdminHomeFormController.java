package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import security.SecurityContextHolder;

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

    public void btnRecordAttendance_OnAction(ActionEvent actionEvent) {
    }

    public void btnViewReports_OnAction(ActionEvent actionEvent) {
    }

    public void btnUserProfile_OnAction(ActionEvent actionEvent) {
    }

    public void btnManageUsers_OnAction(ActionEvent actionEvent) {
    }

    public void btnBackupRestore_OnAction(ActionEvent actionEvent) {
    }

    public void btnSignOut_OnAction(ActionEvent actionEvent) {
    }
}
