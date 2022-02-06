package controller;

import db.DBConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAdminFormController {

    public TextField txtName;
    public TextField txtUserName;
    public PasswordField txtPassword;
    public PasswordField txtConfirmPassword;
    public Button btnCreateAccount;

    public void initialize(){
    }

    public void btnCreateAccount_OnAction(ActionEvent actionEvent) {

        if (!isValidated()){
            return;
        }
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("INSERT INTO user(username, name, password, role) VALUES (?,?,?,?)");
            stm.setString(1,txtName.getText().trim());
            stm.setString(2,txtUserName.getText().trim());
            stm.setString(3,txtPassword.getText().trim());
            stm.setString(4,"ADMIN");
//            stm.executeQuery();
//            stm.execute();
            stm.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,"Your account has been created successfully.");

            /* Redirect to the Login Form */
            AnchorPane root = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
            Scene loginScene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Student Attendance Marking System: Log In");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidated(){
        String name = txtName.getText().trim();
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        if (!name.matches("[A-Za-z]+")){
            new Alert(Alert.AlertType.ERROR, "Please enter a valid name.").show();
            txtName.selectAll();
            txtName.requestFocus();
            return false;
        }
        else if (username.length()<4){
            new Alert(Alert.AlertType.ERROR, "Username should have at least 4 characters.").show();
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return false;
        }
        else if (!username.matches("[]A-Za-z0-9]+")){
            new Alert(Alert.AlertType.ERROR, "Username should contain only characters and digits.").show();
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return false;
        }
        else if (password.length()<4){
            new Alert(Alert.AlertType.ERROR, "Password should have at least 4 characters.").show();
            txtPassword.selectAll();
            txtPassword.requestFocus();
            return false;
        }
        else if (!confirmPassword.equals(password)){
            new Alert(Alert.AlertType.ERROR, "Password mismatch.").show();
            txtConfirmPassword.selectAll();
            txtConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }
}
