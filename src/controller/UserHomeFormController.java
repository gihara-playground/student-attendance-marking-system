package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import security.SecurityContextHolder;

public class UserHomeFormController {

    public Button btnRecordAttendance;
    public Button btnViewReports;
    public Button btnUserProfile;
    public Button btnSignOut;
    public Label lblUser;

    public void initialize(){
        lblUser.setText("Welcome "+ SecurityContextHolder.getPrincipal().getName()+"!");
    }

    public void btnSignOut_OnAction(ActionEvent actionEvent) {
    }

    public void btnUserProfile_OnAction(ActionEvent actionEvent) {
    }

    public void btnViewReports_OnAction(ActionEvent actionEvent) {
    }

    public void btnRecordAttendance_OnAction(ActionEvent actionEvent) {
    }
}
