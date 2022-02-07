package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import security.Principal;
import security.SecurityContextHolder;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public TextField txtUserName;
    public PasswordField txtPassword;
    public Button btnSignIn;

    public void initialize(){

    }

    public void btnSignIn_OnAction(ActionEvent actionEvent) {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR, "Username or password is incorrect.").show();
            return;
        }
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT username,role FROM user WHERE name=? AND password=?");
            stm.setString(1, txtUserName.getText().trim());
            stm.setString(2, txtPassword.getText().trim());
            ResultSet rst = stm.executeQuery();
            if (rst.next()){
                String path = null;
                SecurityContextHolder.setPrincipal(new Principal(
                        txtUserName.getText(),
                        rst.getString("username"),
                        Principal.UserRole.valueOf(rst.getString("role"))
                ));
                if (rst.getString("role").equals("ADMIN")){
                    path="/view/AdminHomeForm.fxml";
                }
                else if (rst.getString("role").equals("USER")){
                    path="/view/UserHomeForm.fxml";
                }
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(path));
                AnchorPane root = fxmlLoader.load();
                Scene homeScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(homeScene);
                stage.setTitle("Student Attendance Marking System: Home");
                stage.sizeToScene();
                stage.centerOnScreen();
                stage.setResizable(false);
                stage.show();

                ((Stage) btnSignIn.getScene().getWindow()).close();
            }else{
                new Alert(Alert.AlertType.ERROR,"Username or password is incorrect.").show();
                txtUserName.selectAll();
                txtUserName.requestFocus();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong. Please try again.");
            e.printStackTrace();
        }

    }

    private boolean isValidated() {
        String username = txtUserName.getText().trim();
        String password = txtPassword.getText().trim();
        if (username.length() < 4 | !username.matches("[]A-Za-z0-9]+") | password.length() < 4) {
            txtUserName.selectAll();
            txtUserName.requestFocus();
            return false;
        }
        return true;
    }

}
