package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RecordAttendanceFormController {

    public TextField txtStudentID;
    public ImageView imgProfile;
    public Button btnIn;
    public Button btnOut;
    public Label lblDate;
    public Label lblID;
    public Label lblName;
    public Label lblStatus;

    public void initialize(){
        LocalDateTime current = LocalDateTime.now();
        lblDate.setText(current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event -> {
            LocalDateTime currentNew = LocalDateTime.now();
            lblDate.setText(currentNew.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        })));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        /*lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event -> {
            lblDate.setText(String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %1$Tp", new Date()));
        })));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();*/
    }
    
    public void btnIn_OnAction(ActionEvent event) {

    }
    
    public void btnOut_OnAction(ActionEvent event) {

    }

    public void txtStudentID_OnAction(ActionEvent event) {

    }

}
